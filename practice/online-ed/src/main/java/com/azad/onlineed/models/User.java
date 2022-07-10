package com.azad.onlineed.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    @NotNull(message = "First name cannot be empty")
    @Size(min = 2, max = 30, message = "First name length has to be between 2 to 30 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Size(min = 2, max = 30, message = "Last name length has to be between 2 to 30 characters")
    private String lastName;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email",
        regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password cannot be empty")
    private String password;
    private boolean enabled;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
