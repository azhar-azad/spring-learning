package com.azad.musiclibrary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @NotNull(message = "Artist name cannot be empty")
    @Size(min = 2, max = 20, message = "Artist name must be between 2 to 20 characters long")
    private String fullName;

    @NotNull(message = "Gender cannot be empty")
    @Size(min = 2, max = 10, message = "Artist gender must be between 2 to 10 characters long")
    private String gender;
}
