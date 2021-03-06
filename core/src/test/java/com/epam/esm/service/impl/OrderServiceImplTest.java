package com.epam.esm.service.impl;

import com.epam.esm.jpa.GiftCertificateJpaRepository;
import com.epam.esm.jpa.OrderJpaRepository;
import com.epam.esm.jpa.UserJpaRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderJpaRepository orderRepository;

    @Mock
    private GiftCertificateJpaRepository giftCertificateRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Integer pageNumber;
    private Integer pageSize;
    private List<OrderEntity> orderEntityList;
    private Timestamp currentTimestamp;
    private PageRequest pageRequest;
    private Page page;

    @BeforeEach
    public void init() {
        pageNumber = 1;
        pageSize = 5;
        pageRequest = PageRequest.of(pageNumber - 1, pageSize);
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
        page = new PageImpl<>(orderEntityList);
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

        Mockito.when(orderRepository.findOrderEntityByUserId(userId, pageRequest)).thenReturn(orderEntityList);
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

    //
    @Test
    void findById() {
        Long id = 1L;
        Mockito.when(orderRepository.findOrderEntityById(id)).thenReturn(orderEntityList.get(0));

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
        Long giftId = 1L;
        Long userId = 1L;
        GiftCertificateEntity giftCertificateEntity = GiftCertificateEntity.builder()
                .id(giftId)
                .price(100)
                .build();
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        Mockito.when(giftCertificateRepository.findGiftCertificateEntityById(giftId)).thenReturn(giftCertificateEntity);
        Mockito.when(userJpaRepository.findUserEntityById(userId)).thenReturn(userEntity);
        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class))).thenReturn(orderEntityList.get(0));

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