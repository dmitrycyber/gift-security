package com.epam.esm.service;

import com.epam.esm.model.entity.UserEntity;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@ToString
public class CustomUserDetails extends User {
    private Long userId;

    public CustomUserDetails(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public CustomUserDetails(UserEntity userEntity) {
        this(
                userEntity.getId(),
                userEntity.getPhoneNumber(),
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole()))
        );
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
