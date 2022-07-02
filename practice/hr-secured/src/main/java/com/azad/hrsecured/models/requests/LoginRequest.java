package com.azad.hrsecured.models.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
