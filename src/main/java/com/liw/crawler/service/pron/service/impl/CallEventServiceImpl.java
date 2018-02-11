package com.liw.crawler.service.pron.service.impl;

import com.liw.crawler.service.pron.dao.CallEventDAO;
import com.liw.crawler.service.pron.entity.CallEvent;
import com.liw.crawler.service.pron.service.CallEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CallEventServiceImpl implements CallEventService {

    @Autowired
    private CallEventDAO callEventDAO;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(CallEvent callEvent) {
        callEventDAO.save(callEvent);
    }

    @Override
    public List<CallEvent> findAll() {
        return callEventDAO.findAll();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteById(String id) {
        callEventDAO.delete(id);
    }
}
