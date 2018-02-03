package com.liw.crawler.service.pron.dao;

import com.liw.crawler.service.pron.entity.PronInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author liwei
 */
public interface PronInfoDAO extends JpaRepository<PronInfo,String>,JpaSpecificationExecutor<PronInfo> {
    /**
     *  统计viewkey的条数
     * @param viewKey
     * @return
     */
    Long countByViewKey(String viewKey);

}
