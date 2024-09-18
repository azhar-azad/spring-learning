package com.azad.tennis_score_api.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Player {

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Player country cannot be empty")
    private String country;

    @NotBlank(message = "Player age cannot be empty")
    private int age;
}
