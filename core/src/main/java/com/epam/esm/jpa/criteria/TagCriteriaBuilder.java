package com.epam.esm.jpa.criteria;

import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TagCriteriaBuilder {
    public CriteriaQuery<TagEntity> build(CriteriaBuilder criteriaBuilder, TagSearchDto tagSearchDto) {
        String tagNamePrefix = tagSearchDto.getTagName();

        CriteriaQuery<TagEntity> criteriaQuery = criteriaBuilder.createQuery(TagEntity.class);
        Root<TagEntity> tagEntityRoot = criteriaQuery.from(TagEntity.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (tagNamePrefix != null) {
            Predicate predicate = criteriaBuilder.like(tagEntityRoot.get(DaoConstants.GIFT_FIELD_NAME),
                    DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD + tagNamePrefix + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
            predicateList.add(predicate);
        }

        criteriaQuery.select(tagEntityRoot).where(predicateList.toArray(new Predicate[0]));
        return criteriaQuery;
    }
}
