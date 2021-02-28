package com.epam.esm.jpa;

import com.epam.esm.model.entity.OrderEntity;

import java.util.List;

public interface OrderRepository {
    /**
     * Find all orders
     *
     * @param pageNumber pagination
     * @param pageSize   pagination
     *                   if fount no orders - return empty list
     * @return List Order Entity
     */
    List<OrderEntity> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Find orders by user id
     *
     * @param userId order id
     * @return OrderEntity
     * @throws com.epam.esm.jpa.exception.UserNotFoundException if such user not exist
     */
    List<OrderEntity> findByUserId(Long userId, Integer pageNumber, Integer pageSize);

    /**
     * Find orders by order id
     *
     * @param orderId order id
     * @return OrderEntity
     * @throws com.epam.esm.jpa.exception.OrderNotFoundException if such order not exist
     */
    OrderEntity findById(Long orderId);

    /**
     * Create order
     *
     * @param orderEntity with gift id and user id
     * @return created order
     */
    OrderEntity createOrder(OrderEntity orderEntity);
}
