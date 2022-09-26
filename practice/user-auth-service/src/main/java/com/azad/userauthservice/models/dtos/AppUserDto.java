package com.azad.userauthservice.models.dtos;

import com.azad.userauthservice.models.AppUser;

public class AppUserDto extends AppUser {

    private Long id;
    private String roleName;

    public AppUserDto() {
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
}
