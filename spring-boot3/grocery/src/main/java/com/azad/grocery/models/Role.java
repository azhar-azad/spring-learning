package com.azad.grocery.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Role {

    @NotNull(message = "Role name cannot be empty")
    protected String roleName;
}
