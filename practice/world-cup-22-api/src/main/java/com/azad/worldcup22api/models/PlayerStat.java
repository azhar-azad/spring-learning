package com.azad.worldcup22api.models;

public class PlayerStat {

    private Integer matchPlayed;
    private Integer goalScored;
    private Integer assistMade;
    private Integer saveCount;
    private Integer yellowCard;
    private Integer redCard;
    private Integer foulsCommitted;

    public PlayerStat() {
    }

    public Integer getMatchPlayed() {
        return matchPlayed;
    }

    public void setMatchPlayed(Integer matchPlayed) {
        this.matchPlayed = matchPlayed;
    }

    public Integer getGoalScored() {
        return goalScored;
    }

    public void setGoalScored(Integer goalScored) {
        this.goalScored = goalScored;
    }

    public Integer getAssistMade() {
        return assistMade;
    }

    public void setAssistMade(Integer assistMade) {
        this.assistMade = assistMade;
    }

    public Integer getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Integer saveCount) {
        this.saveCount = saveCount;
    }

    public Integer getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(Integer yellowCard) {
        this.yellowCard = yellowCard;
    }

    public Integer getRedCard() {
        return redCard;
    }

    public void setRedCard(Integer redCard) {
        this.redCard = redCard;
    }

    public Integer getFoulsCommitted() {
        return foulsCommitted;
    }

    public void setFoulsCommitted(Integer foulsCommitted) {
        this.foulsCommitted = foulsCommitted;
    }
}
