package com.azad.imdbapi.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tv_series")
public class TVSeriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_series_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "start_year", nullable = false)
    private String startYear;

    @Column(name = "end_year")
    private String endYear;

    @Column(name = "imdb_id")
    private String imdbId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "series", cascade = CascadeType.ALL)
    private List<TVSeasonEntity> seasons;

    public TVSeriesEntity() {
    }

    public Long getId() {
        return id;
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


    public List<TVSeasonEntity> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<TVSeasonEntity> seasons) {
        this.seasons = seasons;
    }
}
