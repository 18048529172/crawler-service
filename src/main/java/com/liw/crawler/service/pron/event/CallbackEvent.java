package com.liw.crawler.service.pron.event;

import org.springframework.context.ApplicationEvent;

public class CallbackEvent extends ApplicationEvent {

    public CallbackEvent(Object source) {
        super(source);
    }

    public String getCallId(){
        return (String)this.getSource();
    }
}
