package com.epam.esm.service;

import com.epam.esm.model.dto.user.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Create user
     *
     * @param userDto entity to save
     * @return saved user
     */
    UserDto register(UserDto userDto);

    /**
     * Find user by user id
     *
     * @param userId order id
     * @return UserEntity
     * @throws com.epam.esm.jpa.exception.UserNotFoundException from repository layer if such user not exist
     */
    UserDto userProfile(Long userId);

    /**
     * Find all users
     *
     * @param pageNumber pagination
     * @param pageSize   pagination
     *                   if fount no users - return empty list
     * @return List UserDto
     */
    List<UserDto> findAll(Integer pageNumber, Integer pageSize);
}
