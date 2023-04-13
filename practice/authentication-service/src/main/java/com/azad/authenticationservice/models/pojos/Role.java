package com.azad.authenticationservice.models.pojos;

import javax.validation.constraints.NotNull;

public class Role {

    @NotNull(message = "Role name cannot be empty")
    protected String roleName;
}
