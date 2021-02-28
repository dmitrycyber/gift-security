package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.UserRepository;
import com.epam.esm.jpa.criteria.PaginationBuilder;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.model.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntity register(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity findById(Long userId) {
        UserEntity userEntity = entityManager.find(UserEntity.class, userId);

        if (userEntity == null) {
            throw new UserNotFoundException(userId.toString());
        }
        return userEntity;
    }

    @Override
    public List<UserEntity> findAll(Integer pageNumber, Integer pageSize) {
        TypedQuery<UserEntity> query = entityManager.createQuery("from UserEntity userEntity", UserEntity.class);
        PaginationBuilder.addPagination(pageNumber, pageSize, query);
        return query.getResultList();
    }

    @Override
    public Long findUserIdWithMaxSumOrders() {
        return entityManager.createQuery(
                "SELECT o.userEntity.id FROM OrderEntity o " +
                        "GROUP BY o.userEntity.id " +
                        "ORDER BY sum(o.cost) DESC "
                , Long.class)
                .setMaxResults(1)
                .getSingleResult();
    }
}
