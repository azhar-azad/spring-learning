package com.azad.basicecommerce.auth.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class LoginRequest {

    private String username;
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;
}
