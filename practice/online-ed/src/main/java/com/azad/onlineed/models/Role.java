package com.azad.onlineed.models;

import javax.validation.constraints.NotNull;

public class Role {

    @NotNull(message = "Role name cannot be empty")
    private String roleName;

    public Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
