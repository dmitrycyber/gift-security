package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public UserDto register(@RequestBody @Valid @Validated(CreatingDto.class) UserDto userDto) {
        UserDto savedUser = userService.register(userDto);
        addSelfLinks(savedUser);
        return savedUser;
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public UserDto getUser(@PathVariable Long id) {
        UserDto userDto = userService.userProfile(id);
        addSelfLinks(userDto);
        return userDto;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public List<UserDto> allUsers(Integer pageNumber, Integer pageSize) {
        List<UserDto> userDtoList = userService.findAll(pageNumber, pageSize);
        userDtoList.forEach(this::addSelfLinks);
        return userDtoList;
    }

    private void addSelfLinks(UserDto userDto) {
        userDto.getOrders()
                .forEach(orderDto -> {
                    orderDto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder
                                    .methodOn(OrderControllerImpl.class)
                                    .orderById(orderDto.getId()))
                            .withSelfRel());
                });
        userDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UserControllerImpl.class)
                        .getUser(userDto.getId()))
                .withSelfRel());
    }
}
