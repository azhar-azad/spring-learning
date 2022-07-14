package com.azad.bazaar.models.dtos;

import com.azad.bazaar.models.Member;
import com.azad.bazaar.models.Role;

import java.util.Set;

public class MemberDto extends Member {

    private Long id;
    private Set<String> roleNames;
    private Set<Role> roles;

    public MemberDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
