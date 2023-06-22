package com.azad.grocery.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginRequest {

    private String username;
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;

}
