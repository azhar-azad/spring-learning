package com.azad.wcstatapi.models;

import javax.validation.constraints.NotNull;

public class Team {

    @NotNull(message = "Team name cannot be empty")
    private String teamName;
    private Integer fifaRanking;

    private String coach;
    private Integer groupMatchPlayed;
    private Integer points;
    private Integer groupGoalScored;
    private Integer groupGoalConceded;
    private String tournamentStatus;

    public Team() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String groupName) {
        this.teamName = groupName;
    }

    public Integer getFifaRanking() {
        return fifaRanking;
    }

    public void setFifaRanking(Integer fifaRanking) {
        this.fifaRanking = fifaRanking;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public Integer getGroupMatchPlayed() {
        return groupMatchPlayed;
    }

    public void setGroupMatchPlayed(Integer groupMatchPlayed) {
        this.groupMatchPlayed = groupMatchPlayed;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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

    public String getTournamentStatus() {
        return tournamentStatus;
    }

    public void setTournamentStatus(String tournamentStatus) {
        this.tournamentStatus = tournamentStatus;
    }
}
