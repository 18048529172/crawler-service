package com.liw.crawler.service.pron.event;


import com.alibaba.fastjson.JSONObject;
import com.liw.crawler.service.pron.Constant;
import com.liw.crawler.service.pron.entity.PronEvent;
import com.liw.crawler.service.pron.enums.SystemConfigEnum;
import com.liw.crawler.service.pron.service.PronEventService;
import com.liw.crawler.service.pron.service.PronInfoService;
import com.liw.crawler.service.pron.service.SystemConfigService;
import com.liw.crawler.service.pron.systems.SystemStartHandler;
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
import java.util.Iterator;
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
            //执行
            while(true){
                try{
                    String event = stringRedisTemplate.opsForList().rightPop(Constant.KEY);
                    if(event == null){
                        continue;
                    }
                    PronEvent pronEvent =  JSONObject.toJavaObject(JSONObject.parseObject(event), PronEvent.class);
                    //处理
                    String viewkey = pronEvent.getViewkey();
                    try{
                        String content = getContentByViewKey(viewkey);
                        //处理成功，删除事件
                        //更新内容
                        pronInfoService.updateContent(pronEvent.getOverviewId(),content);
                        pronEventService.delete(pronEvent.getId());
                    }catch (Throwable throwable){
                        LOGGER.error("采集内容出错,id:"+pronEvent.getId(),throwable);
                    }
                }catch (Throwable throwable){
                    LOGGER.error("采集内容出错",throwable);
                }
            }
        });

    }

    public String getContentByViewKey(String viewKey) throws IOException {
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
                return content;
            }
        }
        return  null;
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
