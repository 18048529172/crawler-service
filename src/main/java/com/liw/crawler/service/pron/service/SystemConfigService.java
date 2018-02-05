package com.liw.crawler.service.pron.service;

import com.liw.crawler.service.pron.entity.SystemConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface SystemConfigService {

    void updateProValueByName(String name,String proValue);

    void save(SystemConfig systemConfig);


    void deleteByName( String name );

    List<SystemConfig> listAll();


    String getByName( String name);

}
