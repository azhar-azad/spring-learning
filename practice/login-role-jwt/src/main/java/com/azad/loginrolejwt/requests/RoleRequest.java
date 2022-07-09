package com.azad.loginrolejwt.requests;

import java.util.Set;

public class RoleRequest {

    private String name;
    private Set<String> authorities;

    public RoleRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
