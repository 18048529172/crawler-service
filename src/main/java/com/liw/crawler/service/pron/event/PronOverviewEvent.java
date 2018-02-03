package com.liw.crawler.service.pron.event;

import org.springframework.context.ApplicationEvent;

public class PronOverviewEvent extends ApplicationEvent {

    public PronOverviewEvent(Object source) {
        super(source);
    }

}
