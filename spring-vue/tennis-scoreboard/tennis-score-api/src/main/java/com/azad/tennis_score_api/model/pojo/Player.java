package com.azad.tennis_score_api.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Player {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Player country cannot be empty")
    private String country;

    @NotBlank(message = "Player age cannot be empty")
    private int age;
}
