package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.NotBlank;

public class Role {

    @NotBlank(message = "Role name cannot be empty")
    private String roleName;
}
