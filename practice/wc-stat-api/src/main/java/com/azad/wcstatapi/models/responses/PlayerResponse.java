package com.azad.wcstatapi.models.responses;

import com.azad.wcstatapi.models.Player;
import com.azad.wcstatapi.models.Team;

public class PlayerResponse extends Player {

    private Long id;
    private Team team;

    public PlayerResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
