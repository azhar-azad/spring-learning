package com.azad.authservice.models.responses;

import com.azad.authservice.models.pojos.AppUser;

public class AppUserResponse extends AppUser {

    private Long id;
    private String roleName;

    public AppUserResponse() {
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
