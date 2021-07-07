package com.epam.esm.jpa;

import com.epam.esm.model.entity.UserEntity;

public interface UserJpaRepository extends BaseRepository<UserEntity, Long> {

    /**
     * Find user by user id
     *
     * @param userId order id
     * @return UserEntity
     */
    UserEntity findUserEntityById(Long userId);

    /**
     * Find user by phone number
     *
     * @param phoneNumber user phone number
     * @return UserEntity
     */
    UserEntity findByPhoneNumber(String phoneNumber);
}
