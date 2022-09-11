package com.azad.imdbapi.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TVSeason {

    @NotNull(message = "Season summary cannot be empty")
    @Size(min = 2, message = "Season summary must be of at least 2 characters")
    private String seasonSummary;

    @NotNull(message = "Season number cannot be empty")
    @Min(value = 1, message = "Season number cannot be less then 1")
    private Integer seasonNumber;

    private String imdbId;

    public TVSeason() {
    }

    public String getSeasonSummary() {
        return seasonSummary;
    }

    public void setSeasonSummary(String seasonSummary) {
        this.seasonSummary = seasonSummary;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
