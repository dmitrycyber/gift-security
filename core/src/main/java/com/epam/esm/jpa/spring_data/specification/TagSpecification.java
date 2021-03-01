package com.epam.esm.jpa.spring_data.specification;

import com.epam.esm.jpa.criteria.DaoConstants;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.util.SearchConstants;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TagSpecification {
    public static Specification<TagEntity> bySearchDto(TagSearchDto tagSearchDto){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(DaoConstants.GIFT_FIELD_NAME),
                    DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD + tagSearchDto.getTagName() + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
        };
    }
}
