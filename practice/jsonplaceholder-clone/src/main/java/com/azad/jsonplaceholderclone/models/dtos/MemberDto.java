package com.azad.jsonplaceholderclone.models.dtos;

import com.azad.jsonplaceholderclone.models.Member;
import com.azad.jsonplaceholderclone.models.Role;

public class MemberDto extends Member {

    private Long id;
    private String roleName;
    private Role role;

    public MemberDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
