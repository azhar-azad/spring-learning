package com.azad.moviepedia.models.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;

}
