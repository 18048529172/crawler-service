package com.liw.crawler.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableEurekaClient
@EnableCaching
@EnableFeignClients
public class CrawlerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerServiceApplication.class, args);
	}
}
