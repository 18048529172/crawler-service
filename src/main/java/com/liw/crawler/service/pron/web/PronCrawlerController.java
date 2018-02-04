package com.liw.crawler.service.pron.web;

import com.liw.crawler.service.pron.dao.specification.PageEntity;
import com.liw.crawler.service.pron.dao.specification.PronInfoQuery;
import com.liw.crawler.service.pron.dao.specification.PronInfoSpecificationExecutor;
import com.liw.crawler.service.pron.dao.specification.ResultEntity;
import com.liw.crawler.service.pron.entity.PronInfoOverview;
import com.liw.crawler.service.pron.entity.SystemConfig;
import com.liw.crawler.service.pron.service.PronInfoService;
import com.liw.crawler.service.pron.service.SystemConfigService;
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
   public String addConfig(@RequestBody SystemConfig systemConfig){
        try{
            systemConfigService.save(systemConfig);
        } catch (Exception e){
            return "fail";
        }
        return "ok";
   }

    @PostMapping("/pron/config/update")
    public String updateConfig(@RequestParam("name") String name,@RequestParam("proValue") String proValue){
        try{
            systemConfigService.updateProValueByName(name,proValue);
        } catch (Exception e){
            return "fail";
        }
        return "ok";
    }


    @PostMapping("/pron/config/delete")
    public String deleteConfig(@RequestParam("name") String name){
        try{
            systemConfigService.deleteByName(name);
        } catch (Exception e){
            return "fail";
        }
        return "ok";
    }

    @PostMapping("/pron/config/list")
    public ResultEntity<SystemConfig> listConfig(){
        ResultEntity<SystemConfig> systemConfigPageEntity = new ResultEntity<>();
        systemConfigPageEntity.setRows(this.systemConfigService.listAll());
        return systemConfigPageEntity;
    }








}
