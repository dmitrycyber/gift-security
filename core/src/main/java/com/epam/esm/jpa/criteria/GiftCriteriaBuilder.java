package com.epam.esm.jpa.criteria;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.util.SearchConstants;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.jpa.criteria.DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD;

public class GiftCriteriaBuilder {
    public CriteriaQuery<GiftCertificateEntity> build(CriteriaBuilder criteriaBuilder, GiftSearchDto giftCertificateQueryParameter) {

        CriteriaQuery<GiftCertificateEntity> criteriaQuery = criteriaBuilder.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> giftCertificateRoot = criteriaQuery.from(GiftCertificateEntity.class);

        List<Predicate> predicateList = new ArrayList<>();

        String giftNamePrefix = giftCertificateQueryParameter.getNamePrefix();
        if (giftNamePrefix != null) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get(DaoConstants.GIFT_FIELD_NAME),
                    ZERO_OR_MORE_ELEMENTS_WILDCARD + giftNamePrefix + ZERO_OR_MORE_ELEMENTS_WILDCARD);
            predicateList.add(predicate);
        }

        String giftDescriptionPrefix = giftCertificateQueryParameter.getDescriptionPrefix();
        if (giftDescriptionPrefix != null) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get(DaoConstants.GIFT_FIELD_DESCRIPTION),
                    ZERO_OR_MORE_ELEMENTS_WILDCARD + giftDescriptionPrefix + ZERO_OR_MORE_ELEMENTS_WILDCARD);
            predicateList.add(predicate);
        }

        List<String> tagNamePrefixes = giftCertificateQueryParameter.getTagNamePrefixes();
        if (tagNamePrefixes != null) {
            for (String tagNamePrefix : tagNamePrefixes) {
                Join<GiftCertificateEntity, TagEntity> tagJoin = giftCertificateRoot.join(DaoConstants.GIFT_FIELD_TAG_ENTITIES);
                Predicate predicate = criteriaBuilder.like(tagJoin.get(DaoConstants.GIFT_FIELD_NAME),
                        ZERO_OR_MORE_ELEMENTS_WILDCARD + tagNamePrefix + ZERO_OR_MORE_ELEMENTS_WILDCARD);
                predicateList.add(predicate);
            }
        }

        criteriaQuery.select(giftCertificateRoot).where(predicateList.toArray(new Predicate[0]));

        String sortField = giftCertificateQueryParameter.getSortField();
        String sortMethod = giftCertificateQueryParameter.getSortMethod();

        String columnNameToSort;
        Order order = null;

        if (sortField != null && sortMethod != null) {
            if (sortField.equals(SearchConstants.NAME_FIELD)) {
                columnNameToSort = DaoConstants.GIFT_FIELD_NAME;
                if (sortMethod.equals(SearchConstants.ASC_METHOD_SORT)) {
                    order = criteriaBuilder.asc(giftCertificateRoot.get(columnNameToSort));
                }
                if (sortMethod.equals(SearchConstants.DESC_METHOD_SORT)) {
                    order = criteriaBuilder.desc(giftCertificateRoot.get(columnNameToSort));
                }
            }
            if (sortField.equals(SearchConstants.DATE_FIELD)) {
                columnNameToSort = DaoConstants.GIFT_FIELD_CREATE_DATE;
                if (sortMethod.equals(SearchConstants.ASC_METHOD_SORT)) {
                    order = criteriaBuilder.asc(giftCertificateRoot.get(columnNameToSort));
                }
                if (sortMethod.equals(SearchConstants.DESC_METHOD_SORT)) {
                    order = criteriaBuilder.desc(giftCertificateRoot.get(columnNameToSort));
                }
            }
            criteriaQuery.orderBy(order);
        }
        return criteriaQuery;
    }
}
