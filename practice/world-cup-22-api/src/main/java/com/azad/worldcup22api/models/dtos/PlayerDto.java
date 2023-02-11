package com.azad.worldcup22api.models.dtos;

import com.azad.worldcup22api.models.Player;

public class PlayerDto extends Player {

    private Long id;

    public PlayerDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
