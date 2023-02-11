package com.azad.worldcup22api.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Match {

    private Map<String, Squad> homeTeam;
    private Map<String, Squad> awayTeam;
    private LocalDate matchDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private MatchResult matchResult;

    public Match() {
    }

    public Map<String, Squad> getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Map<String, Squad> homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Map<String, Squad> getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Map<String, Squad> awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public MatchResult getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(MatchResult matchResult) {
        this.matchResult = matchResult;
    }
}
