package com.azad.authservice.models.dtos;

import com.azad.authservice.models.pojos.AppUser;
import com.azad.authservice.models.pojos.Role;

public class AppUserDto extends AppUser {

    private Long id;
    private Role role;
    private String roleName;

    public AppUserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
