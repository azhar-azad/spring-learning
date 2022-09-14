package com.azad.imdbapi.models;

import javax.validation.constraints.NotNull;

public class AuthRole {

    @NotNull(message = "Authentication role name cannot be empty")
    private String roleName;

    public AuthRole() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
