package com.azad.imdbapi.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Genre {

    @NotNull(message = "Genre name cannot be empty")
    @Size(min = 2, message = "Genre name must be of at least 2 characters")
    private String genreName;

    public Genre() {
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
