package com.epam.esm.config.security;

import com.epam.esm.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserSecurity {
    public boolean hasUserId(Authentication authentication, Long userId) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("USER ID FROM REQUEST " + userId);
        log.info("USER ID FROM AUTH " + customUserDetails.getUserId());

        return userId.equals(customUserDetails.getUserId());
    }
}
