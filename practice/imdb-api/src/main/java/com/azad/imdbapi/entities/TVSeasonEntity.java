package com.azad.imdbapi.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tv_season")
public class TVSeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_season_id")
    private Long id;

    @Column(name = "season_summary", nullable = false)
    private String seasonSummary;

    @Column(name = "season_number", nullable = false)
    private Integer seasonNumber;

    @Column(name = "imdb_id")
    private String imdbId;

    @ManyToMany
    @JoinTable(name = "movie_series_person",
            joinColumns = @JoinColumn(name = "tv_season_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<PersonEntity> persons;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tv_series_id")
    private TVSeriesEntity series;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "season", cascade = CascadeType.ALL)
    private List<TVEpisodeEntity> episodes;

    public TVSeasonEntity() {
    }

    public Long getId() {
        return id;
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

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public TVSeriesEntity getSeries() {
        return series;
    }

    public void setSeries(TVSeriesEntity series) {
        this.series = series;
    }

    public List<TVEpisodeEntity> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TVEpisodeEntity> episodes) {
        this.episodes = episodes;
    }
}
