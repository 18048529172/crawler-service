package com.liw.crawler.service.pron.event;


import com.alibaba.fastjson.JSONObject;
import com.liw.crawler.service.pron.entity.PronEvent;
import com.liw.crawler.service.pron.enums.RedisEventListEnum;
import com.liw.crawler.service.pron.enums.SystemConfigEnum;
import com.liw.crawler.service.pron.service.PronEventService;
import com.liw.crawler.service.pron.service.PronInfoService;
import com.liw.crawler.service.pron.service.SystemConfigService;
import com.liw.crawler.service.pron.systems.SystemStartHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Component
public class PropEventHanlder implements SystemStartHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(PropEventHanlder.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PronEventService pronEventService;
    @Autowired
    private PronInfoService pronInfoService;
    @Autowired
    private SystemConfigService systemConfigService;


    public void doEvent(){

        ExecutorService executorService =  new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder().namingPattern("event").daemon(true).build());
        executorService.submit(()->{
            while(true){
                try{
                    String event = stringRedisTemplate.opsForList().rightPop(RedisEventListEnum.KEY.getCode());
                    if(event != null){
                        PronEvent pronEvent =  JSONObject.toJavaObject(JSONObject.parseObject(event), PronEvent.class);
                        //处理
                        String viewkey = pronEvent.getViewkey();
                        try{
                            List<String> contentAndUploadTime = getContentAndUploadTimeByViewKey(viewkey);
                            pronInfoService.updateContentAndUploadTime(pronEvent.getOverviewId(),contentAndUploadTime.get(0),contentAndUploadTime.get(1));
                            pronEventService.delete(pronEvent.getId());
                        }catch (Throwable throwable){
                            LOGGER.error("采集内容出错,id:"+pronEvent.getId(),throwable);
                        }
                    }
                }catch (Throwable throwable){
                    LOGGER.error("采集内容出错",throwable);
                }
            }
        });

    }

    public List<String> getContentAndUploadTimeByViewKey(String viewKey) throws IOException {
        List<String> contentAndUpload = new ArrayList<>();
        String host = this.systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_HOST.getName());
        String pageDetailUrl = this.systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_DETAIL.getName());
        String openDetailUrl = "http://"+host+"/"+pageDetailUrl +"?viewkey="+viewKey;
        Document detailDOC = Jsoup
                .connect(openDetailUrl)
                .header(SystemConfigEnum.PRON_DOMAIN_ACCEPT_LANGUAGE.getName(),systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_ACCEPT_LANGUAGE.getName()))
                .timeout(10000)
                .get();
        Elements spanMore = detailDOC.getElementsByClass("more");
        for(Iterator<Element> iterator2 = spanMore.iterator(); iterator2.hasNext();){
            Element element1 = iterator2.next();
            if("span".equals(element1.tagName())){
                String content = element1.text();
                contentAndUpload.add(content);
                break;
            }
        }
        if(contentAndUpload.isEmpty()){
            contentAndUpload.add("无");
        }
        //<span class="info">添加时间: </span><span class="title">2018-02-10</span>&nbsp;&nbsp;***<br>
        Elements spanElenents = detailDOC.getElementsByTag("span");
        for(Iterator<Element> iterator = spanElenents.iterator();iterator.hasNext();){
            Element spanElement = iterator.next();
            String text = spanElement.text();
            if(StringUtils.contains(text,"添加时间")){
                Element uploadElement = spanElement.nextElementSibling();
                String uploadTime = uploadElement.text();
                contentAndUpload.add(uploadTime);
                break;
            }
        }
        if(contentAndUpload.size() == 1){
            contentAndUpload.add("");
        }
        return  contentAndUpload;
    }


    @Override
    public int sort() {
        return 0;
    }

    @Override
    public void execute() {
        this.doEvent();
    }

    @Override
    public int compareTo(SystemStartHandler o) {
        return this.sort() - o.sort();
    }
}
