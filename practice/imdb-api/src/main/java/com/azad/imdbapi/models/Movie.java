package com.azad.imdbapi.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Movie {

    @NotNull(message = "Movie title cannot be empty")
    @Size(min = 1, message = "Title must be of at least 1 character")
    private String title;

    @NotNull(message = "Movie summary cannot be empty")
    @Size(min = 2, message = "Movie summary must be of at least 2 characters")
    private String summary;

    @NotNull(message = "Year of release cannot be empty")
    @Size(min = 4, message = "Year of release must be of at least 4 characters")
    private String releaseYear;

    @NotNull(message = "Runtime cannot be empty")
    @Size(min = 4, message = "Runtime must be of at least 4 characters")
    private String runtime;

    private String posterPath;

    private Double rating;

    private String imdbId;

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
