package com.azad.authservice.models.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AppUser {

    @NotNull(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name length has to be between 2 to 50 characters.")
    private String name;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull(message = "Username cannot be empty")
    @Size(min = 2, message = "Username has to be more that 2 characters")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public AppUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
