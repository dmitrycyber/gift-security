package com.epam.esm.service.impl;

import com.epam.esm.jpa.UserJpaRepository;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.service.UserService;
import com.epam.esm.util.EntityConverter;
import com.epam.esm.util.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto register(UserDto userDto) {
        UserEntity userEntity = EntityConverter.convertUserDtoToEntity(userDto);

        userEntity.setRole(UserType.ROLE_USER.name());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        UserEntity save = userJpaRepository.save(userEntity);

        return EntityConverter.convertUserEntityToDto(save);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDto userProfile(Long userId) {
        UserEntity userEntity = userJpaRepository.findUserEntityById(userId);
        return EntityConverter.convertUserEntityToDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll(Integer pageNumber, Integer pageSize) {
        List<UserEntity> userEntityList = userJpaRepository.findAll(pageNumber, pageSize);

        return userEntityList.stream()
                .map(EntityConverter::convertUserEntityToDto)
                .collect(Collectors.toList());
    }
}
