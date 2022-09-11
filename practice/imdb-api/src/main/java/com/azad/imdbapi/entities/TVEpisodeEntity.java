package com.azad.imdbapi.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tv_episode")
public class TVEpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_episode_id")
    private Long id;

    @Column(name = "episode_title", nullable = false)
    private String episodeTitle;

    @Column(name = "episode_summary", nullable = false)
    private String episodeSummary;

    @Column(name = "air_date")
    private LocalDate airDate;

    @Column(name = "imdb_id")
    private String imdbId;

    @ManyToMany
    @JoinTable(name = "movie_series_person",
            joinColumns = @JoinColumn(name = "tv_episode_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<PersonEntity> persons;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tv_season_id")
    private TVSeasonEntity season;


    public TVEpisodeEntity() {
    }

    public Long getId() {
        return id;
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

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public TVSeasonEntity getSeason() {
        return season;
    }

    public void setSeason(TVSeasonEntity season) {
        this.season = season;
    }
}
