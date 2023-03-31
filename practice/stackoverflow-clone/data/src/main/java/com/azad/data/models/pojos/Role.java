package com.azad.data.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Role {

    @NotNull(message = "Role name cannot be empty.")
    protected String roleName;
}
