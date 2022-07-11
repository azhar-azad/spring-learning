package com.azad.onlineed.security.requests;

import com.azad.onlineed.models.User;

import java.util.Set;

public class RegistrationRequest extends User {

    private Set<String> roles;

    public RegistrationRequest() {
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
