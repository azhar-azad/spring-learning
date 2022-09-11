package com.azad.imdbapi.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String personName;

    @ManyToMany(mappedBy = "persons")
    private List<MovieEntity> movies;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    @ManyToMany(mappedBy = "persons")
    private List<TVSeasonEntity> tvSeasons;

    @ManyToMany(mappedBy = "persons")
    private List<TVEpisodeEntity> tvEpisodes;

    public PersonEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<TVSeasonEntity> getTvSeasons() {
        return tvSeasons;
    }

    public void setTvSeasons(List<TVSeasonEntity> tvSeasons) {
        this.tvSeasons = tvSeasons;
    }

    public List<TVEpisodeEntity> getTvEpisodes() {
        return tvEpisodes;
    }

    public void setTvEpisodes(List<TVEpisodeEntity> tvEpisodes) {
        this.tvEpisodes = tvEpisodes;
    }
}
