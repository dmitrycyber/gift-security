package com.epam.esm.controller;

import com.epam.esm.model.dto.user.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

@Api(tags = "User Controller")
public interface UserController {
    @ApiOperation(value = "Api v1. Create user")
    UserDto register(UserDto userDto);

    @ApiOperation(value = "Api v1. Get user by id")
    UserDto getUser(Long userId);

    @ApiOperation(value = "Api v1. Get all users")
    List<UserDto> allUsers(
            @ApiParam(name = "pageNumber", value = "pagination page number")
                    Integer pageNumber,
            @ApiParam(name = "pageNumber", value = "pagination page size")
                    Integer pageSize);
}
