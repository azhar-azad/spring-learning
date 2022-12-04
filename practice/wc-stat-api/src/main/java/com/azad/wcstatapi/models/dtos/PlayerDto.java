package com.azad.wcstatapi.models.dtos;

import com.azad.wcstatapi.models.Player;
import com.azad.wcstatapi.models.Team;

public class PlayerDto extends Player {

    private Long id;
    private String teamName;
    private Team team;

    public PlayerDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
