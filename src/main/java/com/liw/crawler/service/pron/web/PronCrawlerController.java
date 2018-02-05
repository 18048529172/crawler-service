package com.liw.crawler.service.pron.web;

import com.liw.crawler.service.pron.dao.specification.PageEntity;
import com.liw.crawler.service.pron.dao.specification.PronInfoQuery;
import com.liw.crawler.service.pron.dao.specification.PronInfoSpecificationExecutor;
import com.liw.crawler.service.pron.dao.specification.ResultEntity;
import com.liw.crawler.service.pron.entity.PronInfoOverview;
import com.liw.crawler.service.pron.entity.SystemConfig;
import com.liw.crawler.service.pron.service.PronInfoService;
import com.liw.crawler.service.pron.service.SystemConfigService;
import com.micro.base.web.response.Body;
import com.micro.base.web.response.Response;
import com.micro.base.web.response.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class PronCrawlerController {

    @Autowired
    private PronInfoService pronInfoService;
    @Autowired
    private SystemConfigService systemConfigService;

    private ResponseTemplate responseTemplate;

    /**
     *  开始
     * @param thread ： 线程数
     * @param deepth ：深度
     * @param startPage ： 开始页
     * @param endPage ： 结束页
     * @return
     */
    @PostMapping("/pron/start/{startPage}/{endPage}/{thread}/{deepth}")
    public Response start(@PathVariable("thread") Integer thread,
                          @PathVariable("deepth") int deepth,
                          @PathVariable("startPage") int startPage,
                          @PathVariable("endPage") int endPage
                        ){
        return this.responseTemplate.doResponse(()->{
            this.pronInfoService.start(startPage,endPage,thread,deepth);
            return null;
        });
    }

    @PostMapping("/pron/stop")
    public Response stop(){
        return this.responseTemplate.doResponse(()->{
            this.pronInfoService.stop();
            return null;
        });
    }

    @GetMapping("/pron/address/{id}")
    public Response address(@PathVariable("id") String id){
        return this.responseTemplate.doResponse(()->{
            String address = this.pronInfoService.getAdress(id);
            return Body.create("openAddress",address);
        });
    }

    @GetMapping("/pron/down/{id}")
    public Response getDownAddress(@PathVariable("id") String id){
        return this.responseTemplate.doResponse(()->{
            String address = this.pronInfoService.getAdress(id);
            return Body.create("downUrl",this.pronInfoService.getDownAddress(id));
        });
    }

    @PostMapping("/pron/list")
   public PageEntity<PronInfoOverview> list(PronInfoQuery pronInfoQuery,
                                            @RequestParam("page") int page,
                                            @RequestParam("rows") int pageSize){
        PronInfoSpecificationExecutor pronInfoSpecificationExecutor = new PronInfoSpecificationExecutor(pronInfoQuery);
        PageRequest pageRequest = new PageRequest((page -1),pageSize);
        Page<PronInfoOverview> pronInfos = this.pronInfoService.find(pronInfoSpecificationExecutor, pageRequest);
        PageEntity<PronInfoOverview> pronInfoPageEntity = new PageEntity<>();
        pronInfoPageEntity.setRows(pronInfos.getContent());
        pronInfoPageEntity.setTotalPage(pronInfos.getTotalPages());
        pronInfoPageEntity.setTotal(pronInfos.getTotalElements());
        return pronInfoPageEntity;
   }

   @PostMapping("/pron/config/add")
   public Response addConfig(@RequestBody SystemConfig systemConfig){
       return this.responseTemplate.doResponse(()->{
           systemConfigService.save(systemConfig);
           return null;
       });

   }

    @PostMapping("/pron/config/update")
    public Response updateConfig(@RequestParam("name") String name,@RequestParam("proValue") String proValue){
        return this.responseTemplate.doResponse(()->{
            systemConfigService.updateProValueByName(name,proValue);
            return null;
        });
    }


    @PostMapping("/pron/config/delete")
    public Response deleteConfig(@RequestParam("name") String name){
        return this.responseTemplate.doResponse(()->{
            systemConfigService.deleteByName(name);
            return null;
        });

    }

    @PostMapping("/pron/config/list")
    public ResultEntity<SystemConfig> listConfig(){
        ResultEntity<SystemConfig> systemConfigPageEntity = new ResultEntity<>();
        systemConfigPageEntity.setRows(this.systemConfigService.listAll());
        return systemConfigPageEntity;
    }








}
