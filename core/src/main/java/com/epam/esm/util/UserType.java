package com.epam.esm.util;

import lombok.Getter;

public enum UserType {

    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    @Getter
    private String title;

    UserType(String title) {
        this.title = title;
    }
}
