package com.epam.esm.jpa.spring_data;

import com.epam.esm.model.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJpaRepository extends BaseRepository<OrderEntity, Long> {

    @Query("select oe from OrderEntity oe where oe.userEntity.id = :userId")
    List<OrderEntity> findOrderEntityByUserId(Long userId, Pageable pageable);

    OrderEntity findOrderEntityById(Long id);
}
