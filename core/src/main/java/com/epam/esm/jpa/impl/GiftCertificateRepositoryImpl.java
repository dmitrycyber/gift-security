package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.jpa.criteria.GiftCriteriaBuilder;
import com.epam.esm.jpa.criteria.PaginationBuilder;
import com.epam.esm.jpa.exception.GiftNotFoundException;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final GiftCriteriaBuilder giftCriteriaBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificateEntity> findAll(Integer pageNumber, Integer pageSize) {
        TypedQuery<GiftCertificateEntity> query = entityManager.createQuery(
                "from GiftCertificateEntity giftCertificate", GiftCertificateEntity.class);

        PaginationBuilder.addPagination(pageNumber, pageSize, query);

        return query.getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAndSortGifts(GiftSearchDto giftSearchDto, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> criteriaQuery = giftCriteriaBuilder.build(criteriaBuilder, giftSearchDto);
        TypedQuery<GiftCertificateEntity> query = entityManager.createQuery(criteriaQuery);

        PaginationBuilder.addPagination(pageNumber, pageSize, query);

        return query.getResultList();
    }

    @Override
    public GiftCertificateEntity findById(Long id) {
        GiftCertificateEntity giftCertificateEntity = entityManager.find(GiftCertificateEntity.class, id);
        if (giftCertificateEntity == null) {
            throw new GiftNotFoundException(id.toString());
        }
        return giftCertificateEntity;
    }

    @Override
    public GiftCertificateEntity createGift(GiftCertificateEntity giftCertificateEntity) {
        entityManager.persist(giftCertificateEntity);
        return giftCertificateEntity;
    }

    @Override
    public GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity) {
        return entityManager.merge(giftCertificateEntity);
    }

    @Override
    public void deleteGift(Long id) {
        GiftCertificateEntity referenceEntity = entityManager.getReference(GiftCertificateEntity.class, id);
        entityManager.remove(referenceEntity);
    }
}
