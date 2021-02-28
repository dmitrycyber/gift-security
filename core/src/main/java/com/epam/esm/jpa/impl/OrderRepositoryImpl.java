package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.OrderRepository;
import com.epam.esm.jpa.criteria.PaginationBuilder;
import com.epam.esm.jpa.exception.OrderNotFoundException;
import com.epam.esm.model.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderEntity> findAll(Integer pageNumber, Integer pageSize) {
        TypedQuery<OrderEntity> query = entityManager.createQuery("from OrderEntity orderEntity", OrderEntity.class);

        PaginationBuilder.addPagination(pageNumber, pageSize, query);

        return query.getResultList();
    }

    @Override
    public List<OrderEntity> findByUserId(Long userId, Integer pageNumber, Integer pageSize) {
        TypedQuery<OrderEntity> query = entityManager.createQuery("from OrderEntity oe WHERE oe.userEntity.id = :userId", OrderEntity.class)
                .setParameter("userId", userId);

        PaginationBuilder.addPagination(pageNumber, pageSize, query);

        return query.getResultList();
    }

    @Override
    public OrderEntity findById(Long orderId) {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, orderId);
        if (orderEntity == null) {
            throw new OrderNotFoundException(orderId.toString());
        }
        return orderEntity;
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }
}
