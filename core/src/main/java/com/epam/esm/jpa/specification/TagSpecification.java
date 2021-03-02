package com.epam.esm.jpa.specification;

import com.epam.esm.jpa.criteria.DaoConstants;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;
import org.springframework.data.jpa.domain.Specification;

public class TagSpecification {
    public static Specification<TagEntity> bySearchDto(TagSearchDto tagSearchDto) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(DaoConstants.GIFT_FIELD_NAME),
                    DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD + tagSearchDto.getTagName() + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
        };
    }
}
