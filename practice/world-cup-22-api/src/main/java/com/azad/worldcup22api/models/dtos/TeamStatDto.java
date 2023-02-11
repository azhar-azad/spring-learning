package com.azad.worldcup22api.models.dtos;

import com.azad.worldcup22api.models.TeamStat;

public class TeamStatDto extends TeamStat {

    private Long id;

    public TeamStatDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
