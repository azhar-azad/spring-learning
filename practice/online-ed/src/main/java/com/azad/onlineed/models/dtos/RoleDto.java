package com.azad.onlineed.models.dtos;

import com.azad.onlineed.models.Authority;
import com.azad.onlineed.models.Role;

import java.util.Set;

public class RoleDto extends Role {


    private Integer id;
    private String roleName;
    private Set<Authority> authorities;
    private Set<String> authorityNames;

    public RoleDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorityNames() {
        return authorityNames;
    }

    public void setAuthorityNames(Set<String> authorityNames) {
        this.authorityNames = authorityNames;
    }
}
