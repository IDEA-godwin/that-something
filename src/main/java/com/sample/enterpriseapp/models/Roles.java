package com.sample.enterpriseapp.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ADMIN, COOK, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
