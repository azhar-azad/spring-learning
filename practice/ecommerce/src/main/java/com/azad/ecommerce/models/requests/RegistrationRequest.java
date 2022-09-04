package com.azad.ecommerce.models.requests;

import com.azad.ecommerce.models.User;

import javax.validation.constraints.NotNull;

public class RegistrationRequest extends User {

    @NotNull(message = "Role name cannot be empty")
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
