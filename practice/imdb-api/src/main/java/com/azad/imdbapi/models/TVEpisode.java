package com.azad.imdbapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class TVEpisode {

    @NotNull(message = "Episode title cannot be empty")
    @Size(min = 1, message = "Episode title must be of at least 1 character")
    private String episodeTitle;

    @NotNull(message = "Episode summary cannot be empty")
    @Size(min = 2, message = "Episode summary must be of at least 2 characters")
    private String episodeSummary;

    @NotNull(message = "Episode air date cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate airDate;

    private String imdbId;

    public TVEpisode() {
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getEpisodeSummary() {
        return episodeSummary;
    }

    public void setEpisodeSummary(String episodeSummary) {
        this.episodeSummary = episodeSummary;
    }

    public LocalDate getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDate airDate) {
        this.airDate = airDate;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
