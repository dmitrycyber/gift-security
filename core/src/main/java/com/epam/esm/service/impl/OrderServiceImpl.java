package com.epam.esm.service.impl;

import com.epam.esm.jpa.exception.GiftNotFoundException;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.jpa.GiftCertificateJpaRepository;
import com.epam.esm.jpa.OrderJpaRepository;
import com.epam.esm.jpa.UserJpaRepository;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final GiftCertificateJpaRepository giftCertificateJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAll(Integer pageNumber, Integer pageSize) {
        List<OrderEntity> orderEntityList = orderJpaRepository.findAll(pageNumber, pageSize);

        return orderEntityList.stream()
                .map(EntityConverter::convertOrderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findByUserId(Long userId, Integer pageNumber, Integer pageSize) {
        List<OrderEntity> orderEntityList = orderJpaRepository.findOrderEntityByUserId(userId, PageRequest.of(pageNumber - 1, pageSize));
        return orderEntityList.stream()
                .map(EntityConverter::convertOrderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto findById(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findOrderEntityById(orderId);

        return EntityConverter.convertOrderEntityToDto(orderEntity);
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Long giftId = orderDto.getGiftId();
        Long userId = orderDto.getUserId();
        GiftCertificateEntity giftCertificateEntity = giftCertificateJpaRepository.findGiftCertificateEntityById(giftId);
        UserEntity userEntity = userJpaRepository.findUserEntityById(userId);

        if (giftCertificateEntity == null) {
            throw new GiftNotFoundException(giftId.toString());
        }
        if (userEntity == null) {
            throw new UserNotFoundException(userId.toString());
        }

        OrderEntity orderEntity = EntityConverter.convertOrderDtoToEntity(orderDto);
        orderEntity.setCost(giftCertificateEntity.getPrice());

        OrderEntity savedOrder = orderJpaRepository.save(orderEntity);

        return EntityConverter.convertOrderEntityToDto(savedOrder);
    }
}
