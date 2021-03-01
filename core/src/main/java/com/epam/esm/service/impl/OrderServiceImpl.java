package com.epam.esm.service.impl;

import com.epam.esm.jpa.spring_data.GiftCertificateJpaRepository;
import com.epam.esm.jpa.spring_data.OrderJpaRepository;
import com.epam.esm.jpa.spring_data.UserJpaRepository;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
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
        Page<OrderEntity> page = orderJpaRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));

        List<OrderEntity> orderEntityList = page.get().collect(Collectors.toList());
        return orderEntityList.stream()
                .map(EntityConverter::convertOrderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findByUserId(Long userId, Integer pageNumber, Integer pageSize) {
        List<OrderEntity> orderEntityList = orderJpaRepository.findOrderEntityByUserId(userId, PageRequest.of(pageNumber, pageSize));
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
        GiftCertificateEntity giftCertificateEntity = giftCertificateJpaRepository.findGiftCertificateEntityById(giftId);
        userJpaRepository.findById(orderDto.getUserId());

        OrderEntity orderEntity = EntityConverter.convertOrderDtoToEntity(orderDto);
        orderEntity.setCost(giftCertificateEntity.getPrice());

        OrderEntity savedOrder = orderJpaRepository.save(orderEntity);

        return EntityConverter.convertOrderEntityToDto(savedOrder);
    }
}
