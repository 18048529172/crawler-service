package com.liw.crawler.service.pron.dao.specification;

import com.liw.crawler.service.pron.entity.PronInfoOverview;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class PronInfoSpecificationExecutor implements Specification<PronInfoOverview> {

    private PronInfoQuery pronInfoQuery;

    public PronInfoSpecificationExecutor(PronInfoQuery pronInfoQuery){
        this.pronInfoQuery = pronInfoQuery;
    }

    @Override
    public Predicate toPredicate(Root<PronInfoOverview> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        //String keyword = this.pronInfoQuery.getKeyword();
        List<Predicate> predicates = new ArrayList<>();
        String title = this.pronInfoQuery.getTitle();
        if(StringUtils.isNotBlank(title)){
            Predicate titleLike = criteriaBuilder.like(root.get("title"),"%"+title+"%");
            predicates.add(titleLike);
        }
        String content = this.pronInfoQuery.getContent();
        if(StringUtils.isNotBlank(content)){
            Predicate titleLike = criteriaBuilder.like(root.get("content"),"%"+content+"%");
            predicates.add(titleLike);
        }
        String author = this.pronInfoQuery.getAuthor();
        if(StringUtils.isNotBlank(author)){
            Predicate authorLike = criteriaBuilder.equal(root.get("author"),author);
            predicates.add(authorLike);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
