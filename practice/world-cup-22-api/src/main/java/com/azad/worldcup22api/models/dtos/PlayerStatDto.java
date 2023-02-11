package com.azad.worldcup22api.models.dtos;

import com.azad.worldcup22api.models.PlayerStat;

public class PlayerStatDto extends PlayerStat {

    private Long id;

    public PlayerStatDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
