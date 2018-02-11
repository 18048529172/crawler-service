package com.liw.crawler.service.pron.event;

import org.springframework.context.ApplicationEvent;

public class PronOverviewEvent extends ApplicationEvent {

    private String callId;

    public PronOverviewEvent(Object source,String callId) {
        super(source);
        this.callId = callId;
    }

    public String getCallId() {
        return callId;
    }
}
