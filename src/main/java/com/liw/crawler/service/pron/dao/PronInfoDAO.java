package com.liw.crawler.service.pron.dao;

import com.liw.crawler.service.pron.entity.PronInfoOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author liwei
 */
public interface PronInfoDAO extends JpaRepository<PronInfoOverview,String>,JpaSpecificationExecutor<PronInfoOverview> {
    /**
     *  统计viewkey的条数
     * @param viewKey
     * @return
     */
    Long countByViewKey(String viewKey);

}
