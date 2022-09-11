package com.azad.imdbapi.models;

import javax.validation.constraints.NotNull;

public class Role {

    @NotNull(message = "Role of the movie person cannot be empty")
    private String roleType;

    public Role() {
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
