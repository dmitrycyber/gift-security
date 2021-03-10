package com.epam.esm.service;

import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.jpa.UserJpaRepository;
import com.epam.esm.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJpaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByPhoneNumber(username);

        if (userEntity == null) {
            throw new UserNotFoundException();
        }
        return new CustomUserDetails(userEntity);
    }
}