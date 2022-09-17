package com.azad.imdbapi.dtos;

import com.azad.imdbapi.models.Genre;
import com.azad.imdbapi.models.Movie;

import java.util.List;

public class MovieDto extends Movie {

    private Long id;
    private List<Genre> genres;

    public MovieDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
