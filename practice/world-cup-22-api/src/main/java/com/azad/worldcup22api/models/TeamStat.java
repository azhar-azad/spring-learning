package com.azad.worldcup22api.models;

public class TeamStat {

    private Integer groupMatchPlayed;
    private Integer groupPointScored;
    private Integer groupGoalScored;
    private Integer groupGoalConceded;
    private String teamStatus;

    public TeamStat() {
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
}
