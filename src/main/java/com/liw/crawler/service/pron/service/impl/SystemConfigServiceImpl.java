package com.liw.crawler.service.pron.service.impl;

import com.liw.crawler.service.pron.dao.SystemConfigDAO;
import com.liw.crawler.service.pron.entity.SystemConfig;
import com.liw.crawler.service.pron.enums.SystemConfigEnum;
import com.liw.crawler.service.pron.service.SystemConfigService;
import com.liw.crawler.service.pron.systems.SystemStartHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemConfigServiceImpl implements SystemConfigService,SystemStartHandler {

    @Autowired
    private SystemConfigDAO systemConfigDAO;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateProValueByName(String name, String proValue) {
        SystemConfig systemConfig = systemConfigDAO.findOneByName(name);
        systemConfig.setProValue(proValue);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(SystemConfig systemConfig) {
        this.systemConfigDAO.save(systemConfig);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteByName(String name) {
        SystemConfig systemConfig = systemConfigDAO.findOneByName(name);
        this.systemConfigDAO.delete(systemConfig);
    }

    @Override
    public List<SystemConfig> listAll() {
        return this.systemConfigDAO.findAll();
    }

    @Override
    public String getByName(String name) {
        return this.systemConfigDAO.findOneByName(name).getProValue();
    }

    @Override
    public int sort() {
        return 1;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void execute() {
        SystemConfigEnum [] systemConfigEnums = SystemConfigEnum.values();
        for(SystemConfigEnum systemConfigEnum : systemConfigEnums){
            long countRows = this.systemConfigDAO.countByName(systemConfigEnum.getName());
            if(countRows > 0){
                continue;
            }
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setName(systemConfigEnum.getName());
            systemConfig.setMemo(systemConfigEnum.getMemo());
            this.systemConfigDAO.save(systemConfig);
        }
    }

    @Override
    public int compareTo(SystemStartHandler o) {
        return this.sort() - o.sort();
    }
}
