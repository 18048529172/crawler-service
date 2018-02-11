package com.liw.crawler.service.pron.service;

import com.liw.crawler.service.pron.entity.CallEvent;

import java.util.List;

public interface CallEventService {

    void save(CallEvent callEvent);

    List<CallEvent> findAll();

    void deleteById(String id);

}
