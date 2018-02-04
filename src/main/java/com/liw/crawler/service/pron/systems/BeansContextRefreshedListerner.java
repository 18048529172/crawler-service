package com.liw.crawler.service.pron.systems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class BeansContextRefreshedListerner implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        SystemStartHandlerFactory systemStartHandlerFactory = contextRefreshedEvent.getApplicationContext().getBean(SystemStartHandlerFactory.class);
        Collection<SystemStartHandler> systemStartHandlerCollections = contextRefreshedEvent.getApplicationContext().getBeansOfType(SystemStartHandler.class).values();
        for(SystemStartHandler handler : systemStartHandlerCollections){
            systemStartHandlerFactory.regist(handler);
        }
    }
}
