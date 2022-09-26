package com.azad.authservice.models.requests;

import com.azad.authservice.models.pojos.AppUser;
import com.azad.authservice.models.pojos.Role;

import javax.validation.constraints.NotNull;

public class RegistrationRequest extends AppUser {

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
