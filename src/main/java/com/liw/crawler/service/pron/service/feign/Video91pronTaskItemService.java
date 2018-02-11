package com.liw.crawler.service.pron.service.feign;

import com.micro.web.response.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "CRAWLER-VIDEO-91PRON-SERVICE",fallback = Video91pronTaskItemServiceImpl.class)
public interface Video91pronTaskItemService {

    @PostMapping("/api/pron/task/callback/{callId}/{mergeDataNumber}")
    Response callback(@PathVariable("callId") String callId,@PathVariable("mergeDataNumber") Integer mergeDataNumber);
}
