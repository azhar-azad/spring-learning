package com.azad.moviepedia.models.pojos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Award {

    @NotBlank(message = "Award name cannot be empty")
    private String name;

    @NotNull(message = "Award year cannot be empty")
    @Min(1900)
    private Integer year;

    @NotBlank(message = "Award category cannot be empty")
    private String category;

    private boolean winner;
}
