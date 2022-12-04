package com.azad.wcstatapi.models.dtos;

import com.azad.wcstatapi.models.Match;

public class MatchDto extends Match {

    private Long id;

    public MatchDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
