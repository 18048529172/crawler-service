package com.liw.crawler.service.pron.service;

import com.liw.crawler.service.pron.entity.PronEvent;

import java.util.List;

public interface PronEventService {

    List<PronEvent> findTop200ByStatusIs0rderByCreatetimeAsc();

    void delete(String id);

    void updateStatus(String id, int status);
}
