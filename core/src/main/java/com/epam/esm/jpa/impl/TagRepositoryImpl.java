package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.TagRepository;
import com.epam.esm.jpa.criteria.PaginationBuilder;
import com.epam.esm.jpa.criteria.TagCriteriaBuilder;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TagRepositoryImpl implements TagRepository {
    private final TagCriteriaBuilder tagCriteriaBuilder;

    private static final String SELECT_MOST_WIDELY_USED_USER_TAG =
            "SELECT t.id, t.created_date, t.last_update_date, t.name FROM orders o " +
                    "JOIN gift_certificates gc on o.gift_id = gc.id " +
                    "JOIN gift_tags gt on gc.id = gt.gift_id " +
                    "JOIN tags t on gt.tag_id = t.id " +
                    "WHERE o.user_id = " +
                    "   (SELECT user_id  " +
                    "    FROM orders o " +
                    "    GROUP BY user_id " +
                    "    ORDER BY sum(o.cost) DESC " +
                    "    LIMIT 1) " +
                    "GROUP BY t.id, t.created_date, t.last_update_date, t.name " +
                    "ORDER BY t.id DESC " +
                    "LIMIT 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @GetMapping
    public List<TagEntity> findAllTags(Integer pageNumber, Integer pageSize) {
        TypedQuery<TagEntity> query = entityManager.createQuery("from TagEntity tagEntity", TagEntity.class);
        PaginationBuilder.addPagination(pageNumber, pageSize, query);
        return query.getResultList();
    }

    @Override
    public List<TagEntity> findAllTags(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteriaQuery = tagCriteriaBuilder.build(criteriaBuilder, tagSearchDto);

        TypedQuery<TagEntity> query = entityManager.createQuery(criteriaQuery);

        PaginationBuilder.addPagination(pageNumber, pageSize, query);
        return query.getResultList();
    }

    @Override
    public TagEntity findTagById(Long tagId) {
        TagEntity tagEntity = entityManager.find(TagEntity.class, tagId);
        if (tagEntity == null) {
            throw new UserNotFoundException(tagId.toString());
        }
        return tagEntity;
    }

    @Override
    public Optional<TagEntity> findTagByName(String tagName) {
        return (Optional<TagEntity>) entityManager.createQuery("from TagEntity tagEntity where name=:tagName")
                .setParameter("tagName", tagName)
                .getResultStream()
                .findFirst();
    }

    @Override
    public TagEntity createTag(TagEntity tagEntity) {
        entityManager.persist(tagEntity);
        return tagEntity;
    }

    @Override
    public void deleteTagById(Long tagId) {
        TagEntity referenceEntity = entityManager.getReference(TagEntity.class, tagId);
        entityManager.remove(referenceEntity);
    }

    @Override
    public TagEntity findMostWidelyUsedUserTag() {
        return (TagEntity) entityManager.createNativeQuery(SELECT_MOST_WIDELY_USED_USER_TAG, TagEntity.class).getSingleResult();
    }
}
