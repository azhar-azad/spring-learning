package com.azad.worldcup22api.models.dtos;

import com.azad.worldcup22api.models.Team;

public class TeamDto extends Team {

    private Long id;

    public TeamDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
