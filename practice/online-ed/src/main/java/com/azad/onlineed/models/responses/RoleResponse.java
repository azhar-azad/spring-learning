package com.azad.onlineed.models.responses;

import com.azad.onlineed.models.Authority;
import com.azad.onlineed.models.Role;

import java.util.Set;

public class RoleResponse extends Role {

    private Integer id;
    private Set<Authority> authorities;

    public RoleResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
