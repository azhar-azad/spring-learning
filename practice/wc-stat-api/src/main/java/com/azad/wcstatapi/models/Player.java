package com.azad.wcstatapi.models;

import com.azad.wcstatapi.models.constants.MatchStatus;
import com.azad.wcstatapi.models.constants.PlayerPosition;
import com.azad.wcstatapi.validations.EnumValidator;

import javax.validation.constraints.NotNull;

public class Player {

    @NotNull(message = "Player name cannot be empty")
    private String playerName;

    private Integer age;

    @NotNull(message = "Player position cannot be empty. GK/DEF/MID/FWD")
    @EnumValidator(enumClass = PlayerPosition.class, message = "Not a valid player position")
    private String position;

    private Integer matchCount;
    private Integer goalCount;
    private Integer assistCount;
    private Integer saveCount;

    public Player() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public Integer getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(Integer goalCount) {
        this.goalCount = goalCount;
    }

    public Integer getAssistCount() {
        return assistCount;
    }

    public void setAssistCount(Integer assistCount) {
        this.assistCount = assistCount;
    }

    public Integer getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Integer saveCount) {
        this.saveCount = saveCount;
    }
}
