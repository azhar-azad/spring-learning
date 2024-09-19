package com.azad.tennis_score_api.model.pojo;

import com.azad.tennis_score_api.model.constant.ServeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Point {

    @Enumerated(EnumType.STRING)
    private ServeType serveType;

    @NotBlank(message = "Score cannot be empty")
    private String score; // Example: 30-15
}
