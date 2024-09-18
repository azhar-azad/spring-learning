package com.azad.tennis_score_api.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Tournament {

    @NotBlank(message = "Tournament name cannot be empty")
    private String tournamentName;

    @NotBlank(message = "Country cannot be empty")
    private String country;
}
