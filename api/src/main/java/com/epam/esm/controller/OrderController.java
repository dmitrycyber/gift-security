package com.epam.esm.controller;

import com.epam.esm.model.dto.order.OrderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

@Api(tags = "Order Controller")
public interface OrderController {
    @ApiOperation(value = "Api v1. Get all orders")
    List<OrderDto> allOrders(
            @ApiParam(name = "user id", value = "for get user orders")
                    Long userId,
            @ApiParam(name = "pageNumber", value = "pagination page number")
                    Integer pageNumber,
            @ApiParam(name = "pageNumber", value = "pagination page size")
                    Integer pageSize);

    @ApiOperation(value = "Api v1. Get order by id")
    OrderDto orderById(Long orderId);

    @ApiOperation(value = "Api v1. Create order")
    OrderDto createOrder(
            @ApiParam(name = "orderDto", value = "with filled user id and gift id")
                    OrderDto orderDto);
}
