package com.azad.data.models.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AppUser {

    @NotNull(message = "First name cannot be empty.")
    @Size(min = 1, max = 50, message = "First name length has to be between 1 to 50 characters.")
    protected String firstName;

    @NotNull(message = "Last name cannot be empty.")
    @Size(min = 1, max = 50, message = "Last name length has to be between 1 to 50 characters.")
    protected String lastName;

    @NotNull(message = "Email cannot be empty.")
    @Email(message = "Please provide a valid email",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    protected String email;

    @NotNull(message = "Username cannot be empty")
    @Size(min = 2, message = "Username has to be more that 2 characters")
    protected String username;

    @NotNull(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;

    protected String jobTitle;
    protected String companyName;
    protected String location;
    protected Integer reputation;
}
