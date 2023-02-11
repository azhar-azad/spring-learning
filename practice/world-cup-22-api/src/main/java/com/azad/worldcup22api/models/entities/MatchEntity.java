package com.azad.worldcup22api.models.entities;

import com.azad.worldcup22api.models.Squad;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @Column(name = "match_time")
    private LocalDateTime matchTime;

    @Column(name = "home_team")
    private String homeTeamName;

    @Column(name = "away_team")
    private String awayTeamName;

    @Column(name = "home_goal")
    private Integer homeTeamGoalScored;

    @Column(name = "away_goal")
    private Integer awayTeamGoalScored;

    @Column(name = "winner")
    private String winnerTeamName;

    @Column(name = "looser")
    private String looserTeamName;

    public MatchEntity() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Integer getHomeTeamGoalScored() {
        return homeTeamGoalScored;
    }

    public void setHomeTeamGoalScored(Integer homeTeamGoalScored) {
        this.homeTeamGoalScored = homeTeamGoalScored;
    }

    public Integer getAwayTeamGoalScored() {
        return awayTeamGoalScored;
    }

    public void setAwayTeamGoalScored(Integer awayTeamGoalScored) {
        this.awayTeamGoalScored = awayTeamGoalScored;
    }

    public String getWinnerTeamName() {
        return winnerTeamName;
    }

    public void setWinnerTeamName(String winnerTeamName) {
        this.winnerTeamName = winnerTeamName;
    }

    public String getLooserTeamName() {
        return looserTeamName;
    }

    public void setLooserTeamName(String looserTeamName) {
        this.looserTeamName = looserTeamName;
    }
}
