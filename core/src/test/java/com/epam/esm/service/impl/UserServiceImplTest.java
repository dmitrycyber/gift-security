package com.epam.esm.service.impl;

import com.epam.esm.jpa.UserJpaRepository;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.model.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserJpaRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private Integer pageNumber;
    private Integer pageSize;
    private List<UserEntity> userEntityList;
    private Timestamp currentTimestamp;
    private PageRequest pageRequest;
    private Page page;

    @BeforeEach
    public void init() {
        pageNumber = 1;
        pageSize = 5;
        userEntityList = new ArrayList<>();
        currentTimestamp = new Timestamp(System.currentTimeMillis());
        pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        LongStream.range(1, 6)
                .forEach(index -> {
                    userEntityList.add(UserEntity.builder()
                            .id(index)
                            .phoneNumber("+375291847896")
                            .password("eyqwe.mzc;vzxcvlizjxcvljn,n")
                            .email("mail@mail.com")
                            .firstName("Ivan")
                            .lastName("Ivanov")
                            .createDate(currentTimestamp)
                            .lastUpdate(currentTimestamp)
                            .build());
                });
        page = new PageImpl<>(userEntityList);
    }

    @Test
    void register() {
        String expectedPhoneNumber = "+375291847896";
        String expectedPassword = "123--password";
        String expectedEncodedPassword = "eyqwe.mzc;vzxcvlizjxcvljn,n";
        String expectedEmail = "mail@mail.com";
        String expectedName = "Ivan";
        String expectedLastName = "Ivanov";

        UserDto userToSave = UserDto.builder()
                .phoneNumber(expectedPhoneNumber)
                .password(expectedPassword)
                .email(expectedEmail)
                .firstName(expectedName)
                .lastName(expectedLastName)
                .build();

        Mockito.when(userRepository.save(Mockito.any(UserEntity.class)))
                .thenReturn(userEntityList.get(0));

        Mockito.when(passwordEncoder.encode(expectedPassword)).thenReturn(expectedEncodedPassword);

        UserDto userDto = userService.register(userToSave);

        Assertions.assertNotNull(userDto);
        Assertions.assertNotNull(userDto.getId());
        Assertions.assertNotNull(userDto.getEmail());
        Assertions.assertNotNull(userDto.getFirstName());
        Assertions.assertNotNull(userDto.getLastName());
        Assertions.assertNotNull(userDto.getPhoneNumber());
        Assertions.assertEquals(1L, userDto.getId());
        Assertions.assertEquals(expectedLastName, userDto.getLastName());
        Assertions.assertEquals(expectedName, userDto.getFirstName());
        Assertions.assertEquals(expectedEmail, userDto.getEmail());
        Assertions.assertEquals(expectedPhoneNumber, userDto.getPhoneNumber());
    }

    @Test
    void userProfile() {
        long id = 1L;
        Mockito.when(userRepository.findUserEntityById(Mockito.anyLong())).thenReturn(userEntityList.get(0));

        UserDto userDto = userService.userProfile(id);

        Assertions.assertNotNull(userDto);
        Assertions.assertNotNull(userDto.getId());
        Assertions.assertNotNull(userDto.getEmail());
        Assertions.assertNotNull(userDto.getFirstName());
        Assertions.assertNotNull(userDto.getLastName());
        Assertions.assertNotNull(userDto.getPhoneNumber());
        Assertions.assertEquals(1L, userDto.getId());
    }

    @Test
    void findAll() {
        Mockito.when(userRepository.findAll(pageNumber, pageSize)).thenReturn(userEntityList);
        List<UserDto> userDtoList = userService.findAll(pageNumber, pageSize);

        Assertions.assertEquals(5, userDtoList.size());
        userDtoList
                .forEach(userDto -> {
                    Assertions.assertNotNull(userDto.getId());
                    Assertions.assertNotNull(userDto.getPhoneNumber());
                    Assertions.assertNotNull(userDto.getEmail());
                    Assertions.assertNotNull(userDto.getFirstName());
                    Assertions.assertNotNull(userDto.getLastName());
                });
    }
}