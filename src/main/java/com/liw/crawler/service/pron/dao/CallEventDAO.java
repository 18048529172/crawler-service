package com.liw.crawler.service.pron.dao;

import com.liw.crawler.service.pron.entity.CallEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallEventDAO extends JpaRepository<CallEvent,String> {
}
