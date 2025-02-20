package com.smart_bike_rent.security.userrole;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    DEVEL,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
