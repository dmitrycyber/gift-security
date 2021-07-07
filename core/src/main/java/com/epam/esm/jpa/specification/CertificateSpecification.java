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
        String sortField = giftSearchDto.getSortField();
        String sortMethod = giftSearchDto.getSortMethod();

        Specification<GiftCertificateEntity> specificationBeforeOrdering = (Specification<GiftCertificateEntity>) (root, query, criteriaBuilder) -> null;

        if (giftNamePrefix != null) {
            specificationBeforeOrdering = Specification
                    .where(specificationBeforeOrdering
                            .and(giftNameLike(giftNamePrefix)));
        }
        if (giftDescriptionPrefix != null) {
            specificationBeforeOrdering = Specification
                    .where(specificationBeforeOrdering
                            .and(giftDescriptionLike(giftDescriptionPrefix)));
        }
        if (tagNamePrefixes != null) {
            specificationBeforeOrdering = Specification
                    .where(specificationBeforeOrdering
                            .and(giftTagNamePrefixesLike(tagNamePrefixes)));
        }

        Specification<GiftCertificateEntity> finalSpecification = specificationBeforeOrdering;
        return (root, query, criteriaBuilder) -> {
            defineSortMethodAndColumn(sortField, sortMethod, root, query, criteriaBuilder);

            return finalSpecification.toPredicate(root, query, criteriaBuilder);
        };
    }

    private static Specification<GiftCertificateEntity> giftNameLike(String giftNamePrefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(DaoConstants.GIFT_FIELD_NAME),
                giftNamePrefix + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
    }

    private static Specification<GiftCertificateEntity> giftDescriptionLike(String giftDescriptionPrefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(DaoConstants.GIFT_FIELD_DESCRIPTION),
                giftDescriptionPrefix + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
    }

    private static Specification<GiftCertificateEntity> giftTagNamePrefixesLike(List<String> tagNames) {
        return (Specification<GiftCertificateEntity>) (root, query, criteriaBuilder) -> criteriaBuilder
                .in(root
                        .join(DaoConstants.GIFT_FIELD_TAG_ENTITIES)
                        .get(DaoConstants.TAG_FIELD_NAME))
                .value(tagNames);
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
