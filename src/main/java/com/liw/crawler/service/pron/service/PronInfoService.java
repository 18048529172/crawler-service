package com.liw.crawler.service.pron.service;

import com.liw.crawler.service.pron.dao.specification.PronInfoSpecificationExecutor;
import com.liw.crawler.service.pron.entity.PronInfoOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PronInfoService {

    /**
     * 保存
     * @param pronInfo
     */
    void save(PronInfoOverview pronInfo);

    /**
     * 查询viewkey是否存在
     * @param viewKey
     * @return
     */
    long countByViewKey(String viewKey);

    /**
     * 开始
     * @param startPage
     * @param threadNumber
     * @param deepth
     */
    void start(int startPage,int endPage,int threadNumber,int deepth,String callId);

    /**
     *  获取地址
     * @param id
     * @return
     */
    String getAdress(String id);


    /**
     *  更新
     * @param id
     * @param content
     */
    void updateContentAndUploadTime(String id, String content,String uploadTime);

    /**
     * 分页查询
     * @param pronInfoSpecificationExecutor
     * @param pageRequest
     * @return
     */
    Page<PronInfoOverview> find(PronInfoSpecificationExecutor pronInfoSpecificationExecutor, PageRequest pageRequest);

    /**
     *  获取下载地址
     * @param id
     * @return
     */
    String getDownAddress(String id);

    PronInfoOverview findById(String id);

    Long  countByCallId(String callId);

}
