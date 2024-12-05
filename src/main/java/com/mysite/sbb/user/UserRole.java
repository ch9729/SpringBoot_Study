package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    //User 권한들을 적는다.
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
