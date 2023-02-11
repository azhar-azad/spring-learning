package com.azad.worldcup22api.models;

public class Player {

    private String playerName;
    private Integer jerseyNo;
    private Integer age;
    private String position;

    public Player() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getJerseyNo() {
        return jerseyNo;
    }

    public void setJerseyNo(Integer jerseyNo) {
        this.jerseyNo = jerseyNo;
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
}
