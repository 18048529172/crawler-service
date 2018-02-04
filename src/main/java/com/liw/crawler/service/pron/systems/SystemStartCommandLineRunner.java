package com.liw.crawler.service.pron.systems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class SystemStartCommandLineRunner implements CommandLineRunner {

    @Autowired
    private SystemStartHandlerFactory systemStartHandlerFactory;

    @Override
    public void run(String... strings) throws Exception {
        for(SystemStartHandler handler : systemStartHandlerFactory.getSystemStartHandlers()){
            handler.execute();
        }
    }

}
