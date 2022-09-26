package com.azad.userauthservice.models.requests;

import com.azad.userauthservice.models.AppUser;

import javax.validation.constraints.NotNull;

public class RegistrationRequest extends AppUser {

    @NotNull(message = "User role name cannot be empty")
    private String roleName;

    public RegistrationRequest() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
