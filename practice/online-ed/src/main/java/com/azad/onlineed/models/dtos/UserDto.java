package com.azad.onlineed.models.dtos;

import com.azad.onlineed.models.User;

import java.util.Set;

public class UserDto extends User {

    private Set<String> roles;

    public UserDto() {
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
