package com.liw.crawler.service;

import com.micro.web.response.ResponseTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 说明：
 *
 * @author liw@suncd.com
 * @date 2017/12/31 17:23
 */
@Configuration
public class ReponseConfig {

    @Bean
    public ResponseTemplate builder(){
        ResponseTemplate responseTemplate = new ResponseTemplate("请求错误");
        return responseTemplate;
    }
}
