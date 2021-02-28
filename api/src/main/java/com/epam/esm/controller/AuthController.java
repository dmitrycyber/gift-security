package com.epam.esm.controller;

import com.epam.esm.model.dto.user.AuthDto;
import com.epam.esm.model.dto.user.TokenDto;
import com.epam.esm.model.dto.user.UserDto;
import io.swagger.annotations.ApiOperation;

public interface AuthController {
    @ApiOperation(value = "Api v1. Auth user")
    TokenDto auth(AuthDto authDto);
}
