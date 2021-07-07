package com.epam.esm.controller;

import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.dto.search.PaginationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Api(tags = "Order Controller")
public interface OrderController {
    @ApiOperation(value = "Api v1. Get all orders")
    List<OrderDto> allOrders(
            @ApiParam(name = "user id", value = "for get user orders")
                    Long userId,
            @ApiParam(name = "paginationDto", value = "pagination page object")
                    PaginationDto paginationDto,
            Authentication authentication);

    @ApiOperation(value = "Api v1. Get order by id")
    OrderDto orderById(Long orderId);

    @ApiOperation(value = "Api v1. Create order")
    OrderDto createOrder(
            @ApiParam(name = "orderDto", value = "with filled user id and gift id")
                    OrderDto orderDto,
            Authentication authentication);
}
