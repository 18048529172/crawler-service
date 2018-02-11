package com.liw.crawler.service.pron.scheduled;

import com.liw.crawler.service.pron.entity.CallEvent;
import com.liw.crawler.service.pron.entity.PronEvent;
import com.liw.crawler.service.pron.enums.RedisEventListEnum;
import com.liw.crawler.service.pron.service.CallEventService;
import com.liw.crawler.service.pron.service.PronEventService;
import com.liw.crawler.service.pron.service.PronInfoService;
import com.liw.crawler.service.pron.service.feign.Video91pronTaskItemService;
import com.micro.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PronEventScheduled {

    @Autowired
    private PronEventService pronEventService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CallEventService callEventService;
    @Autowired
    private PronInfoService pronInfoService;
    @Autowired
    private Video91pronTaskItemService video91pronTaskItemService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void loadTop1Event(){
        long size = this.stringRedisTemplate.opsForList().size(RedisEventListEnum.KEY.getCode());
        if(size == 0){
            List<PronEvent> pronEventList = pronEventService.findTop200ByStatusIs0rderByCreatetimeAsc();
            for(PronEvent pronEvent : pronEventList){
                this.stringRedisTemplate.opsForList().leftPush(RedisEventListEnum.KEY.getCode(),pronEvent.toString());
            }
        }
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void callbackEvent(){
        List<CallEvent> callEvents = callEventService.findAll();
        for(CallEvent callEvent : callEvents){
            String callId =  callEvent.getCallbackId();
            Long number = pronInfoService.countByCallId(callId);
            //回调
            Integer mergeDataCount = Integer.valueOf(String.valueOf(number));
            Response response = video91pronTaskItemService.callback(callId,mergeDataCount);
            if(response.isSuccess()){
                //删除
                callEventService.deleteById(callEvent.getId());
            }
        }
    }

}
