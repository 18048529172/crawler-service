package com.liw.crawler.service.pron.service;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
import com.liw.crawler.service.pron.enums.SystemConfigEnum;
import com.liw.crawler.service.pron.event.CallbackEvent;
import com.liw.crawler.service.pron.event.PronOverviewEvent;
import com.liw.crawler.service.pron.service.helper.PronDocUtils;
import com.liw.crawler.service.pron.service.helper.PronOverview;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author liwei
 */
//@Service
public class PronCrawler extends RamCrawler {


    private ApplicationEventPublisher applicationEventPublisher;

    private SystemConfigService systemConfigService;

    private String callId;

    public PronCrawler(
            ApplicationEventPublisher applicationEventPublisher,
            SystemConfigService systemConfigService,
            String callId) {
        super(false);
        this.applicationEventPublisher = applicationEventPublisher;
        this.systemConfigService = systemConfigService;
        this.callId = callId;
    }

    @Override
    public Page getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum);
        request.addHeader(SystemConfigEnum.PRON_DOMAIN_ACCEPT_LANGUAGE.getName(),systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_ACCEPT_LANGUAGE.getName()));
        return request.responsePage();
    }

   /**
     * @param page
     * @param next
     */
    @Override
    public void visit(Page page, CrawlDatums next) {
        Document document = page.doc();
        List<PronOverview> pronOverviews = PronDocUtils.parser(document);
        PronOverviewEvent pronOverviewEvent = new PronOverviewEvent(pronOverviews,callId);
        this.applicationEventPublisher.publishEvent(pronOverviewEvent);
    }

    private String getStartPage(int pageNumber) {
        String host = this.systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_HOST.getName());
        String pageList = this.systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_PAGE_LIST.getName());
        String page =  "http://"+host+"/"+pageList;
        return page + pageNumber;
    }

    @Override
    public void afterStop() {
       //停止后执行回调
       this.applicationEventPublisher.publishEvent(new CallbackEvent(this.callId));
    }
}
