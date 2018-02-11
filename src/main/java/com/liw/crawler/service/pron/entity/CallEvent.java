package com.liw.crawler.service.pron.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CallEvent {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;

    private String callbackId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

}
