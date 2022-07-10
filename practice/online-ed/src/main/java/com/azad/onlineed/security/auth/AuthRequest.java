package com.azad.onlineed.security.auth;

import com.azad.onlineed.models.User;

import java.util.Set;

public class AuthRequest extends User {

    private Set<String> roles;

    public AuthRequest() {
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
