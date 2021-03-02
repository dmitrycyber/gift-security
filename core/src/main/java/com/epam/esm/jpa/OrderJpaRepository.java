package com.epam.esm.jpa;

import com.epam.esm.model.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJpaRepository extends BaseRepository<OrderEntity, Long> {

    /**
     * Find orders by user id
     *
     * @param userId order id
     * @return OrderEntity
     */
    @Query("select oe from OrderEntity oe where oe.userEntity.id = :userId")
    List<OrderEntity> findOrderEntityByUserId(Long userId, Pageable pageable);
    /**
     * Find orders by order id
     *
     * @param id order id
     * @return OrderEntity
     * @throws com.epam.esm.jpa.exception.OrderNotFoundException if such order not exist
     */
    OrderEntity findOrderEntityById(Long id);
}
