package com.azad.imdbapi.requests;

import com.azad.imdbapi.models.Genre;
import com.azad.imdbapi.models.Movie;

import java.util.List;

public class MovieRequest extends Movie {

    private List<Genre> genres;

    public MovieRequest() {
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
