package com.liw.crawler.service.pron.service.impl;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import com.liw.crawler.service.pron.dao.PronEventDAO;
import com.liw.crawler.service.pron.dao.PronInfoDAO;
import com.liw.crawler.service.pron.dao.specification.PronInfoSpecificationExecutor;
import com.liw.crawler.service.pron.entity.PronEvent;
import com.liw.crawler.service.pron.entity.PronInfo;
import com.liw.crawler.service.pron.service.PronCrawler;
import com.liw.crawler.service.pron.service.PronInfoService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
public class PronInfoServiceImpl  implements PronInfoService {

    @Autowired
    private PronInfoDAO pronInfoDAO;
    @Autowired
    private PronEventDAO pronEventDAO;
    @Autowired
    private PronCrawler pronCrawler;

    @Value("${pron.domain.host}")
    private String prondomainhost;
    @Value("${pron.domain.video.page.list}")
    private String pronPageList;

    @Value("${pron.domain.video.page.detail}")
    private String pageDetailUrl;

    private final static ExecutorService executorService=new ScheduledThreadPoolExecutor(50,new BasicThreadFactory.Builder().namingPattern("crawler").daemon(true).build());


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(PronInfo pronInfo) {
        //判断是否存在
        long viewkeyCount = this.countByViewKey(pronInfo.getViewKey());
        if(viewkeyCount == 0){
            pronInfoDAO.save(pronInfo);
            PronEvent pronEvent = new PronEvent();
            pronEvent.setCreateTime(new Date());
            pronEvent.setOverviewId(pronInfo.getId());
            pronEvent.setStatus(0);
            pronEvent.setViewkey(pronInfo.getViewKey());
            pronEventDAO.save(pronEvent);
        }

    }



    @Override
    public long countByViewKey(String viewKey) {
        return pronInfoDAO.countByViewKey(viewKey);
    }

    @Override
    public void start(int startPage,int endPage, int threadNumber, int deepth) {
        executorService.submit(()->{
            pronCrawler.setThreads(threadNumber);
            for(int i=startPage;i<=endPage;i++){
                String seedUrl = getStartPage(i);
                pronCrawler.addSeed(new CrawlDatum(seedUrl).meta("page",i));
            }
            try {
                pronCrawler.start(deepth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getAdress(String id) {
        PronInfo pronInfo = this.pronInfoDAO.findOne(id);
        return "http://"+this.prondomainhost+"/"+this.pageDetailUrl +"?viewkey="+pronInfo.getViewKey();
    }

    @Override
    public void stop() {
        this.pronCrawler.stop();
    }

    private String getStartPage(int pageNumber) {
        String page =  "http://"+this.prondomainhost+"/"+this.pronPageList;
        return page + pageNumber;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateContent(String id, String content) {
        PronInfo pronInfo = this.pronInfoDAO.findOne(id);
        pronInfo.setContent(content);
    }

    @Override
    public Page<PronInfo> find(PronInfoSpecificationExecutor pronInfoSpecificationExecutor, PageRequest pageRequest) {
        return this.pronInfoDAO.findAll(pronInfoSpecificationExecutor,pageRequest);
    }

    @Override
    public String getDownAddress(String id) {
        Connection con = Jsoup.connect("http://www.caijilian.com/index.php");
        con.data("url", this.getAdress(id));
        try {
            Document doc = con.post();
            Element element = doc.getElementsByAttributeValue("placeholder","下载地址").first();
            return element.attr("value");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
