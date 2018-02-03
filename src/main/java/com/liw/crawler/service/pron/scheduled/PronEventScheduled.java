package com.liw.crawler.service.pron.scheduled;

import com.liw.crawler.service.pron.Constant;
import com.liw.crawler.service.pron.entity.PronEvent;
import com.liw.crawler.service.pron.service.PronEventService;
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

    @Scheduled(cron = "0/10 * * * * ?")
    public void loadTop1Event(){
        long size = this.stringRedisTemplate.opsForList().size(Constant.KEY);
        if(size != 0){
            return ;
        }
        List<PronEvent> pronEventList = pronEventService.findTop200ByStatusIs0rderByCreatetimeAsc();
        for(PronEvent pronEvent : pronEventList){
            this.stringRedisTemplate.opsForList().leftPush(Constant.KEY,pronEvent.toString());
        }
    }


}