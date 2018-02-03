package com.liw.crawler.service.pron.dao.specification;

import com.liw.crawler.service.pron.entity.PronInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class PronInfoSpecificationExecutor implements Specification<PronInfo> {

    private PronInfoQuery pronInfoQuery;

    public PronInfoSpecificationExecutor(PronInfoQuery pronInfoQuery){
        this.pronInfoQuery = pronInfoQuery;
    }

    @Override
    public Predicate toPredicate(Root<PronInfo> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        String keyword = this.pronInfoQuery.getKeyword();
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.isNotBlank(keyword)){
            Predicate titleLike = criteriaBuilder.like(root.get("title"),"%"+keyword+"%");
            Predicate contentLike = criteriaBuilder.like(root.get("content"),"%"+keyword+"%");
            Predicate keywordLike = criteriaBuilder.or(titleLike,contentLike);
            predicates.add(keywordLike);
        }
        String author = this.pronInfoQuery.getAuthor();
        if(StringUtils.isNotBlank(author)){
            Predicate authorLike = criteriaBuilder.like(root.get("author"),"%"+author+"%");
            predicates.add(authorLike);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
