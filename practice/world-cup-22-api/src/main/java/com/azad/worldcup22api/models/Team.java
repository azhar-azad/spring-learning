package com.azad.worldcup22api.models;

public class Team {

    private String teamName;
    private String continent;
    private Integer fifaRanking;
    private String coachName;

    public Team() {
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
}
