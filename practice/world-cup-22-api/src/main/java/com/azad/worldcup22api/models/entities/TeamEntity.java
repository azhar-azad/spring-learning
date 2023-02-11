package com.azad.worldcup22api.models.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teams")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", unique = true)
    private String teamName;

    @Column(name = "continent")
    private String continent;

    @Column(name = "fifa_ranking")
    private Integer fifaRanking;

    @Column(name = "coach_name")
    private String coachName;

    @OneToOne(mappedBy = "team")
    private TeamStatEntity teamStat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    private List<PlayerEntity> players;

    public TeamEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getFifaRanking() {
        return fifaRanking;
    }

    public void setFifaRanking(Integer fifaRanking) {
        this.fifaRanking = fifaRanking;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public TeamStatEntity getTeamStat() {
        return teamStat;
    }

    public void setTeamStat(TeamStatEntity teamStat) {
        this.teamStat = teamStat;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }
}
