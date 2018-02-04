package com.liw.crawler.service.pron.service;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.liw.crawler.service.pron.enums.SystemConfigEnum;
import com.liw.crawler.service.pron.event.PronOverviewEvent;
import com.liw.crawler.service.pron.service.helper.PronDocUtils;
import com.liw.crawler.service.pron.service.helper.PronOverview;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwei
 */
@Service
public class PronCrawler extends BreadthCrawler {


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public PronCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    public PronCrawler() {
        this("91pron", false);
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
        PronOverviewEvent pronOverviewEvent = new PronOverviewEvent(pronOverviews);
        this.applicationEventPublisher.publishEvent(pronOverviewEvent);
    }

    private String getStartPage(int pageNumber) {
        String host = this.systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_HOST.getName());
        String pageList = this.systemConfigService.getByName(SystemConfigEnum.PRON_DOMAIN_PAGE_LIST.getName());
        String page =  "http://"+host+"/"+pageList;
        return page + pageNumber;
    }



}
