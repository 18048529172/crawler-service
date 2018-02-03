package com.liw.crawler.service.pron.dao;

import com.liw.crawler.service.pron.entity.PronEvent;
import com.liw.crawler.service.pron.entity.PronInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PronEventDAO extends JpaRepository<PronEvent,String>,JpaSpecificationExecutor<PronEvent> {

    List<PronEvent> findTop200ByStatusIsOrderByCreateTimeAsc(Integer status);
}
