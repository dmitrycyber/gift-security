package com.epam.esm.jpa.spring_data;

import com.epam.esm.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserJpaRepository extends BaseRepository<UserEntity, Long> {
    UserEntity findUserEntityById(Long userId);

    UserEntity findByPhoneNumber(String phoneNumber);

    UserEntity findByPhoneNumberAndPassword(String phoneNumber, String password);
}
