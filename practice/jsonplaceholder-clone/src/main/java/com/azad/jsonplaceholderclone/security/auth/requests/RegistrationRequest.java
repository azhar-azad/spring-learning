package com.azad.jsonplaceholderclone.security.auth.requests;

import com.azad.jsonplaceholderclone.models.Member;

import javax.validation.constraints.NotNull;

public class RegistrationRequest extends Member {

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
