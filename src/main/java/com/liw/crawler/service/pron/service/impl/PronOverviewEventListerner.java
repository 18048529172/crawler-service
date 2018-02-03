package com.liw.crawler.service.pron.service.impl;

import com.liw.crawler.service.pron.entity.PronInfo;
import com.liw.crawler.service.pron.event.PronOverviewEvent;
import com.liw.crawler.service.pron.service.PronInfoService;
import com.liw.crawler.service.pron.service.helper.PronOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PronOverviewEventListerner implements ApplicationListener<PronOverviewEvent> {

    @Autowired
    private PronInfoService pronInfoService;

    @Override
    public void onApplicationEvent(PronOverviewEvent pronOverviewEvent) {
        List<PronOverview> pronOverviews = (List<PronOverview>) pronOverviewEvent.getSource();
        for(PronOverview pronOverview : pronOverviews){
            PronInfo pronInfo = new PronInfo();
            pronInfo.setTitle(pronOverview.getTitle());
            pronInfo.setAuthor(pronOverview.getAuthor());
            pronInfo.setViewKey(pronOverview.getViewkey());
            this.pronInfoService.save(pronInfo);
        }
    }

}
