package com.azad.authenticationservice.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Role {

    @NotNull(message = "Role name cannot be empty")
    protected String roleName;
}
