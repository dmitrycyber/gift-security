package com.epam.esm.controller;

import com.epam.esm.model.dto.search.PaginationDto;
import com.epam.esm.model.dto.user.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.Authentication;

import java.util.List;

@Api(tags = "User Controller")
public interface UserController {
    @ApiOperation(value = "Api v1. Create user")
    UserDto register(UserDto userDto);

    @ApiOperation(value = "Api v1. Get user by id")
    UserDto getUser(Long userId, Authentication authentication);

    @ApiOperation(value = "Api v1. Get all users")
    List<UserDto> allUsers(
            @ApiParam(name = "paginationDto", value = "pagination page object")
                    PaginationDto paginationDto);
}
