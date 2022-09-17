package com.azad.imdbapi.responses;

import com.azad.imdbapi.models.Genre;
import com.azad.imdbapi.models.Movie;

import java.util.List;

public class MovieResponse extends Movie {

    private Long id;

    private List<String> genres;

    public MovieResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
