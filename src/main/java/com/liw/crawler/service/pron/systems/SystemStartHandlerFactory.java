package com.liw.crawler.service.pron.systems;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SystemStartHandlerFactory {

    private final List<SystemStartHandler> systemStartHandlers = new ArrayList<>();

    public void regist(SystemStartHandler systemStartHandler){
        this.systemStartHandlers.add(systemStartHandler);
        Collections.sort(systemStartHandlers);
    }

    public List<SystemStartHandler> getSystemStartHandlers(){
        return this.systemStartHandlers;
    }

}
