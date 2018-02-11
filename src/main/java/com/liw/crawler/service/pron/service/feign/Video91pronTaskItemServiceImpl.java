package com.liw.crawler.service.pron.service.feign;

import com.micro.web.response.Meta;
import com.micro.web.response.Response;
import org.springframework.stereotype.Service;

@Service
public class Video91pronTaskItemServiceImpl implements Video91pronTaskItemService {
    @Override
    public Response callback(String callId, Integer mergeDataNumber) {
        Response response = new Response();
        Meta meta = new Meta();
        meta.setCode("-1");
        meta.setMessage("接口调用失败");
        response.setMeta(meta);
        return response;
    }
}
