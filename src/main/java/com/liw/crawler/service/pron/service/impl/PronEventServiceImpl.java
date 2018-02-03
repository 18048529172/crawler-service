package com.liw.crawler.service.pron.service.impl;

import com.liw.crawler.service.pron.dao.PronEventDAO;
import com.liw.crawler.service.pron.entity.PronEvent;
import com.liw.crawler.service.pron.service.PronEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PronEventServiceImpl implements PronEventService {

    @Autowired
    private PronEventDAO pronEventDAO;

    @Override
    public List<PronEvent> findTop200ByStatusIs0rderByCreatetimeAsc() {
        return pronEventDAO.findTop200ByStatusIsOrderByCreateTimeAsc(0);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(String id) {
        pronEventDAO.delete(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateStatus(String id, int status) {
        PronEvent pronEvent = this.pronEventDAO.findOne(id);
        pronEvent.setStatus(status);
    }
}
