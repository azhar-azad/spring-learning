package com.azad.imdbapi.requests;

import com.azad.imdbapi.models.ImdbUser;

import javax.validation.constraints.NotNull;

public class RegistrationRequest extends ImdbUser {

    @NotNull(message = "Authentication role name cannot be empty")
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
