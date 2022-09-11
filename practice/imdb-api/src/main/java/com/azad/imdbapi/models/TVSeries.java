package com.azad.imdbapi.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TVSeries {

    @NotNull(message = "TV Series title cannot be empty")
    @Size(min = 1, message = "TV Series title must be of at least 1 character")
    private String title;

    @NotNull(message = "TV Series summary cannot be empty")
    @Size(min = 2, message = "TV Series summary must be of at least 2 characters")
    private String summary;

    @NotNull(message = "Year of starting cannot be empty")
    @Size(min = 4, message = "Year of starting must be of at least 4 characters")
    private String startYear;

    private String endYear;

    private String imdbId;

    public TVSeries() {
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

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
