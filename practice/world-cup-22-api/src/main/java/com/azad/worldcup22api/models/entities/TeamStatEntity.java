package com.azad.worldcup22api.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "team_stats")
public class TeamStatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_stat_id")
    private Long id;

    @Column(name = "group_match_played")
    private Integer groupMatchPlayed;

    @Column(name = "group_point_scored")
    private Integer groupPointScored;

    @Column(name = "group_goal_scored")
    private Integer groupGoalScored;

    @Column(name = "group_goal_conceded")
    private Integer groupGoalConceded;

    @Column(name = "team_status")
    private String teamStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamEntity team;

    public TeamStatEntity() {
    }

    public Long getId() {
        return id;
    }

    public Integer getGroupMatchPlayed() {
        return groupMatchPlayed;
    }

    public void setGroupMatchPlayed(Integer groupMatchPlayed) {
        this.groupMatchPlayed = groupMatchPlayed;
    }

    public Integer getGroupPointScored() {
        return groupPointScored;
    }

    public void setGroupPointScored(Integer groupPointScored) {
        this.groupPointScored = groupPointScored;
    }

    public Integer getGroupGoalScored() {
        return groupGoalScored;
    }

    public void setGroupGoalScored(Integer groupGoalScored) {
        this.groupGoalScored = groupGoalScored;
    }

    public Integer getGroupGoalConceded() {
        return groupGoalConceded;
    }

    public void setGroupGoalConceded(Integer groupGoalConceded) {
        this.groupGoalConceded = groupGoalConceded;
    }

    public String getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(String teamStatus) {
        this.teamStatus = teamStatus;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }
}
