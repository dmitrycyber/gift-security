package com.epam.esm.config.security;

import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class UserSecurity {
    public boolean hasUserId(Principal principal, Long userId) {
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;

        return userId.equals(customUserDetails.getUserId());
    }
}
