package com.liw.crawler.service.pron.web;

import com.liw.crawler.service.pron.dao.specification.PronInfoQuery;
import com.liw.crawler.service.pron.dao.specification.PronInfoSpecificationExecutor;
import com.liw.crawler.service.pron.entity.PronInfoOverview;
import com.liw.crawler.service.pron.service.PronInfoService;
import com.liw.crawler.service.pron.service.SystemConfigService;
import com.micro.web.response.Body;
import com.micro.web.response.Response;
import com.micro.web.response.ResponseTemplate;
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

    @Autowired
    private ResponseTemplate responseTemplate;

    /**
     *  开始
     * @param thread ： 线程数
     * @param deepth ：深度
     * @param startPage ： 开始页
     * @param endPage ： 结束页
     * @return
     */
    @PostMapping("/pron/start/{startPage}/{endPage}/{thread}/{deepth}/{callId}")
    public Response start(@PathVariable("thread") Integer thread,
                          @PathVariable("deepth") int deepth,
                          @PathVariable("startPage") int startPage,
                          @PathVariable("endPage") int endPage,
                          @PathVariable("callId") String callId
                        ){
        return this.responseTemplate.doResponse(()->{
            this.pronInfoService.start(startPage,endPage,thread,deepth,callId);
            return null;
        });
    }


    /**
     *  打开地址
     * @param id
     * @return
     */
    @GetMapping("/pron/address/{id}")
    public Response address(@PathVariable("id") String id){
        return this.responseTemplate.doResponse(()->{
            String address = this.pronInfoService.getAdress(id);
            return Body.create("openAddress",address);
        });
    }

    /**
     *  下载地址
     * @param id
     * @return
     */
    @GetMapping("/pron/down/{id}")
    public Response getDownAddress(@PathVariable("id") String id){
        return this.responseTemplate.doResponse(()->{
            String address = this.pronInfoService.getAdress(id);
            return Body.create("downUrl",this.pronInfoService.getDownAddress(id));
        });
    }

    /**
     *  分页查询
      * @param pronInfoQuery
     * @param page
     * @param pageSize
     * @return
     */
   @PostMapping("/pron/list")
   public Response list(PronInfoQuery pronInfoQuery,
                                            @RequestParam("page") int page,
                                            @RequestParam("rows") int pageSize){
        return this.responseTemplate.doResponse(()->{
            PronInfoSpecificationExecutor pronInfoSpecificationExecutor = new PronInfoSpecificationExecutor(pronInfoQuery);
            PageRequest pageRequest = new PageRequest((page -1),pageSize);
            Page<PronInfoOverview> pronInfos = this.pronInfoService.find(pronInfoSpecificationExecutor, pageRequest);
            return Body
                    .create("rows",pronInfos.getContent())
                    .append("totalRows",pronInfos.getTotalElements())
                    .append("totalPage",pronInfos.getTotalPages());
        });

   }

    /**
     *  修改配置
     * @param name
     * @param proValue
     * @return
     */
    @PostMapping("/pron/config/update")
    public Response updateConfig(@RequestParam("name") String name,@RequestParam("proValue") String proValue){
        return this.responseTemplate.doResponse(()->{
            systemConfigService.updateProValueByName(name,proValue);
            return null;
        });
    }

    /**
     *  删除配置
     * @param name
     * @return
     */
    @PostMapping("/pron/config/delete")
    public Response deleteConfig(@RequestParam("name") String name){
        return this.responseTemplate.doResponse(()->{
            systemConfigService.deleteByName(name);
            return null;
        });

    }

    /**
     *  查询所有配置
     * @return
     */
    @GetMapping("/pron/config/list")
    public Response listConfig(){
        return this.responseTemplate.doResponse(()->{
            return Body.create("rows",this.systemConfigService.listAll());
        });
    }


}
