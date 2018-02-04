package com.liw.crawler.service.pron.enums;

public enum SystemConfigEnum {

    PRON_DOMAIN_HOST("pron.domain.host","网站域名"),
    PRON_DOMAIN_DETAIL("pron.domain.video.page.detail","详情地址"),
    PRON_DOMAIN_PAGE_LIST("pron.domain.video.page.list","列表地址")
    ;

    private SystemConfigEnum( String name,String memo){
        this.name = name;
        this.memo = memo;
    }

    private String name;

    private String memo;

    public String getName() {
        return name;
    }

    public String getMemo() {
        return memo;
    }
}
