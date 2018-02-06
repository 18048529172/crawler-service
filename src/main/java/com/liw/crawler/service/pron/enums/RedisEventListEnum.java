package com.liw.crawler.service.pron.enums;

public enum RedisEventListEnum {

    KEY("key","redis_event_list")
    ;

    private String code;
    private String name;

    private RedisEventListEnum(String name,String code){
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
