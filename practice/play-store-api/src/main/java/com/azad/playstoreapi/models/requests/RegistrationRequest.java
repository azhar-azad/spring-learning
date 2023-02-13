package com.azad.playstoreapi.models.requests;

import com.azad.playstoreapi.models.pojos.PlayStoreUser;

import javax.validation.constraints.NotNull;

public class RegistrationRequest extends PlayStoreUser {

    @NotNull(message = "Role name cannot be empty")
    private String roleName;

    public RegistrationRequest() {}

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
