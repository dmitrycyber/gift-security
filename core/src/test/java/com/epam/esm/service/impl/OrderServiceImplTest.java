package com.epam.esm.service.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.jpa.OrderRepository;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Integer pageNumber;
    private Integer pageSize;
    private List<OrderEntity> orderEntityList;
    private Timestamp currentTimestamp;

    @BeforeEach
    public void init() {
        pageNumber = 1;
        pageSize = 5;
        currentTimestamp = new Timestamp(System.currentTimeMillis());
        orderEntityList = new ArrayList<>();

        LongStream.range(1, 6)
                .forEach(index -> {
                    orderEntityList.add(OrderEntity.builder()
                            .id(index)
                            .cost((int) index)
                            .userEntity(UserEntity.builder()
                                    .id(index)
                                    .build())
                            .giftCertificateEntity(GiftCertificateEntity.builder()
                                    .id(index)
                                    .build())
                            .purchaseDate(currentTimestamp)
                            .build());
                });
    }

    @Test
    void findAll() {
        Mockito.when(orderRepository.findAll(pageNumber, pageSize)).thenReturn(orderEntityList);
        List<OrderDto> orderDtoList = orderService.findAll(pageNumber, pageSize);
        Assertions.assertEquals(5, orderDtoList.size());
        orderDtoList
                .forEach(orderDto -> {
                    Assertions.assertNotNull(orderDto.getId());
                    Assertions.assertNotNull(orderDto.getCost());
                    Assertions.assertNotNull(orderDto.getGiftId());
                    Assertions.assertNotNull(orderDto.getUserId());
                    Assertions.assertNotNull(orderDto.getPurchaseDate());
                });
    }

    @Test
    void findByUserId() {
        Long userId = 1L;

        Mockito.when(orderRepository.findByUserId(userId, pageNumber, pageSize)).thenReturn(orderEntityList);
        List<OrderDto> orderDtoList = orderService.findByUserId(userId, pageNumber, pageSize);
        Assertions.assertEquals(5, orderDtoList.size());
        orderDtoList
                .forEach(orderDto -> {
                    Assertions.assertNotNull(orderDto.getId());
                    Assertions.assertNotNull(orderDto.getCost());
                    Assertions.assertNotNull(orderDto.getGiftId());
                    Assertions.assertNotNull(orderDto.getUserId());
                    Assertions.assertNotNull(orderDto.getPurchaseDate());
                });
    }

    @Test
    void findById() {
        long id = 1L;
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(orderEntityList.get(0));

        OrderDto orderDto = orderService.findById(id);

        Assertions.assertNotNull(orderDto);
        Assertions.assertNotNull(orderDto.getId());
        Assertions.assertNotNull(orderDto.getPurchaseDate());
        Assertions.assertNotNull(orderDto.getUserId());
        Assertions.assertNotNull(orderDto.getGiftId());
        Assertions.assertEquals(1L, orderDto.getId());
        Assertions.assertEquals(1L, orderDto.getGiftId());
        Assertions.assertEquals(1L, orderDto.getUserId());
    }

    @Test
    void createOrder() {
        GiftCertificateEntity giftCertificateEntity = GiftCertificateEntity.builder()
                .id(1L)
                .price(100)
                .build();

        Mockito.when(orderRepository.createOrder(Mockito.any(OrderEntity.class))).thenReturn(orderEntityList.get(0));
        Mockito.when(giftCertificateRepository.findById(Mockito.anyLong())).thenReturn(giftCertificateEntity);

        OrderDto orderDto = orderService.createOrder(OrderDto.builder()
                .userId(1L)
                .giftId(1L)
                .build());

        Assertions.assertNotNull(orderDto);
        Assertions.assertNotNull(orderDto.getId());
        Assertions.assertNotNull(orderDto.getPurchaseDate());
        Assertions.assertNotNull(orderDto.getUserId());
        Assertions.assertNotNull(orderDto.getGiftId());
        Assertions.assertEquals(1L, orderDto.getId());
        Assertions.assertEquals(1L, orderDto.getGiftId());
        Assertions.assertEquals(1L, orderDto.getUserId());
    }
}