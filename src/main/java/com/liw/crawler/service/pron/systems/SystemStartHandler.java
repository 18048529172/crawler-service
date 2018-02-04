package com.liw.crawler.service.pron.systems;

public interface SystemStartHandler extends Comparable<SystemStartHandler>{

    int sort();

    void execute();
}
