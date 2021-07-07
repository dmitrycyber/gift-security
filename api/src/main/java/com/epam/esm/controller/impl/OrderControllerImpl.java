package com.epam.esm.controller.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.dto.search.PaginationDto;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(T(com.epam.esm.util.UserType).ROLE_USER.name()) " +
            "and @userSecurity.hasUserId(#authentication, #userId) " +
            "or hasRole(T(com.epam.esm.util.UserType).ROLE_ADMIN.name()) ")
    public List<OrderDto> allOrders(
            Long userId,
            @Valid PaginationDto paginationDto,
            @AuthenticationPrincipal Authentication authentication) {
        Integer pageNumber = paginationDto.getPageNumber();
        Integer pageSize = paginationDto.getPageSize();

        List<OrderDto> orderDtoList = userId == null
                ? orderService.findAll(pageNumber, pageSize)
                : orderService.findByUserId(userId, pageNumber, pageSize);
        orderDtoList.forEach(this::addSelfLinks);
        return orderDtoList;
    }

    @Override
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(T(com.epam.esm.util.UserType).ROLE_USER.name()) " +
            "or hasRole(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public OrderDto orderById(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        addSelfLinks(orderDto);
        return orderDto;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(T(com.epam.esm.util.UserType).ROLE_USER.name()) " +
            "and @userSecurity.hasUserId(#authentication, #orderDto.userId) " +
            "or hasRole(T(com.epam.esm.util.UserType).ROLE_ADMIN.name()) ")
    public OrderDto createOrder(
            @RequestBody @Valid @Validated(CreatingDto.class) OrderDto orderDto,
            @AuthenticationPrincipal Authentication authentication) {
        OrderDto savedOrder = orderService.createOrder(orderDto);
        addSelfLinks(savedOrder);
        return savedOrder;
    }

    private void addSelfLinks(OrderDto orderDto) {
        orderDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(OrderControllerImpl.class)
                        .orderById(orderDto.getId()))
                .withSelfRel());
    }
}
