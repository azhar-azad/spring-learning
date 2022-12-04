package com.azad.wcstatapi.models.requests;

import com.azad.wcstatapi.models.Player;

public class PlayerRequest extends Player {

    private String teamName;

    public PlayerRequest() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
