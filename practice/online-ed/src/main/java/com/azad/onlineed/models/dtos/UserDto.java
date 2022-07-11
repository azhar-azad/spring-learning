package com.azad.onlineed.models.dtos;

import com.azad.onlineed.models.Role;
import com.azad.onlineed.models.User;

import java.util.Set;

public class UserDto extends User {

    private Long id;
    private Set<Role> roles;
    private Set<String> roleNames;

    private String username; // for student entity

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
