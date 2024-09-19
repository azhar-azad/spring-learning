package com.azad.tennis_score_api.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Match {

    @NotBlank(message = "Round cannot be empty")
    private String round;

    private String status; // ongoing, finished, etc
}
