package com.azad.erecruitment.models.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Role {

    @NotNull(message = "Role name cannot be empty")
    protected String roleName;
}
