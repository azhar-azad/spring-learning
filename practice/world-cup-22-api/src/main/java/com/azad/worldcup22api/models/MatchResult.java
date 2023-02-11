package com.azad.worldcup22api.models;

public class MatchResult {

    private Integer homeTeamScored;
    private Integer awayTeamScored;

    public MatchResult() {
    }

    public Integer getHomeTeamScored() {
        return homeTeamScored;
    }

    public void setHomeTeamScored(Integer homeTeamScored) {
        this.homeTeamScored = homeTeamScored;
    }

    public Integer getAwayTeamScored() {
        return awayTeamScored;
    }

    public void setAwayTeamScored(Integer awayTeamScored) {
        this.awayTeamScored = awayTeamScored;
    }
}
