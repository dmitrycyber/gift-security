package com.epam.esm.jpa.specification;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.util.DaoConstants;
import com.epam.esm.util.SearchConstants;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CertificateSpecification {
    public static Specification<GiftCertificateEntity> bySearchRequest(GiftSearchDto giftSearchDto) {
        String giftNamePrefix = giftSearchDto.getNamePrefix();
        String giftDescriptionPrefix = giftSearchDto.getDescriptionPrefix();
        List<String> tagNamePrefixes = giftSearchDto.getTagNamePrefixes();

        Specification<GiftCertificateEntity> finalSpec = (Specification<GiftCertificateEntity>) (root, query, criteriaBuilder) -> null;

        if (giftNamePrefix != null) {
            finalSpec = Specification
                    .where(finalSpec
                            .and(giftNameLike(giftNamePrefix, giftSearchDto.getSortField(), giftSearchDto.getSortMethod())));
        }
        if (giftDescriptionPrefix != null) {
            finalSpec = Specification
                    .where(finalSpec
                            .and(giftDescriptionLike(giftDescriptionPrefix, giftSearchDto.getSortField(), giftSearchDto.getSortMethod())));
        }
        if (tagNamePrefixes != null) {
            finalSpec = Specification
                    .where(finalSpec
                            .and(giftTagNamePrefixesLike(tagNamePrefixes, giftSearchDto.getSortField(), giftSearchDto.getSortMethod())));
        }

        return finalSpec;
    }

    private static Specification<GiftCertificateEntity> giftNameLike(String giftNamePrefix, String sortField, String sortMethod) {
        return (root, query, criteriaBuilder) -> {
            defineSortMethodAndColumn(sortField, sortMethod, root, query, criteriaBuilder);

            return criteriaBuilder.like(root.get(DaoConstants.GIFT_FIELD_NAME),
                    DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD + giftNamePrefix + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
        };
    }

    private static Specification<GiftCertificateEntity> giftDescriptionLike(String giftDescriptionPrefix, String sortField, String sortMethod) {

        return (root, query, criteriaBuilder) -> {
            defineSortMethodAndColumn(sortField, sortMethod, root, query, criteriaBuilder);

            return criteriaBuilder.like(root.get(DaoConstants.GIFT_FIELD_DESCRIPTION),
                    DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD + giftDescriptionPrefix + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
        };
    }

    private static Specification<GiftCertificateEntity> giftTagNamePrefixesLike(List<String> tagNames, String sortField, String sortMethod) {
        return (Specification<GiftCertificateEntity>) (root, query, criteriaBuilder) -> {
            defineSortMethodAndColumn(sortField, sortMethod, root, query, criteriaBuilder);

            return criteriaBuilder
                    .in(root
                            .join(DaoConstants.GIFT_FIELD_TAG_ENTITIES)
                            .get(DaoConstants.GIFT_FIELD_NAME))
                    .value(tagNames);
        };
    }

    private static void defineSortMethodAndColumn(String sortField, String sortMethod, Root<GiftCertificateEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (SearchConstants.NAME_FIELD.equals(sortField)) {
            if (SearchConstants.ASC_METHOD_SORT.equals(sortMethod)) {
                query.orderBy(criteriaBuilder.asc(root.get(SearchConstants.NAME_FIELD)));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(SearchConstants.NAME_FIELD)));
            }
        }
        if (SearchConstants.DATE_FIELD.equals(sortField)) {
            if (SearchConstants.ASC_METHOD_SORT.equals(sortMethod)) {
                query.orderBy(criteriaBuilder.asc(root.get(DaoConstants.GIFT_FIELD_CREATE_DATE)));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(DaoConstants.GIFT_FIELD_CREATE_DATE)));
            }
        }
    }
}
