package com.liw.crawler.service.pron.dao;

import com.liw.crawler.service.pron.entity.PronInfoOverview;
import com.liw.crawler.service.pron.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SystemConfigDAO extends JpaRepository<SystemConfig,String>,JpaSpecificationExecutor<SystemConfig> {


    SystemConfig findOneByName(String name);

    long countByName( String name);
}
