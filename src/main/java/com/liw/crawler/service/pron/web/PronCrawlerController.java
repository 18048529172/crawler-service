package com.liw.crawler.service.pron.web;

import com.liw.crawler.service.pron.dao.specification.PageEntity;
import com.liw.crawler.service.pron.dao.specification.PronInfoQuery;
import com.liw.crawler.service.pron.dao.specification.PronInfoSpecificationExecutor;
import com.liw.crawler.service.pron.entity.PronInfo;
import com.liw.crawler.service.pron.entity.dto.PronInfoQueryObject;
import com.liw.crawler.service.pron.service.PronInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class PronCrawlerController {

    @Autowired
    private PronInfoService pronInfoService;

    /**
     *  开始
     * @param thread ： 线程数
     * @param deepth ：深度
     * @param startPage ： 开始页
     * @param endPage ： 结束页
     * @return
     */
    @PostMapping("/pron/start/{startPage}/{endPage}/{thread}/{deepth}")
    public String start(@PathVariable("thread") Integer thread,
                        @PathVariable("deepth") int deepth,
                        @PathVariable("startPage") int startPage,
                        @PathVariable("endPage") int endPage
                        ){
       try{
          this.pronInfoService.start(startPage,endPage,thread,deepth);
       } catch (Exception e){
           return "fail";
       }
       return "ok";
    }

    @PostMapping("/pron/stop")
    public String stop(){
        try{
            this.pronInfoService.stop();
        } catch (Exception e){
            return "fail";
        }
        return "ok";
    }

    @GetMapping("/pron/address/{id}")
    public String address(@PathVariable("id") String id){
        return this.pronInfoService.getAdress(id);
    }

    @GetMapping("/pron/down/{id}")
    public String getDownAddress(@PathVariable("id") String id){
        return this.pronInfoService.getDownAddress(id);
    }


    @PostMapping("/pron/list")
   public PageEntity<PronInfo> list(PronInfoQuery pronInfoQuery,
                                    @RequestParam("page") int page,
                                    @RequestParam("rows") int pageSize){
        PronInfoSpecificationExecutor pronInfoSpecificationExecutor = new PronInfoSpecificationExecutor(pronInfoQuery);
        PageRequest pageRequest = new PageRequest((page -1),pageSize);
        Page<PronInfo> pronInfos = this.pronInfoService.find(pronInfoSpecificationExecutor, pageRequest);
        PageEntity<PronInfo> pronInfoPageEntity = new PageEntity<>();
        pronInfoPageEntity.setRows(pronInfos.getContent());
        pronInfoPageEntity.setTotalPage(pronInfos.getTotalPages());
        pronInfoPageEntity.setTotal(pronInfos.getTotalElements());
        return pronInfoPageEntity;
   }


}
