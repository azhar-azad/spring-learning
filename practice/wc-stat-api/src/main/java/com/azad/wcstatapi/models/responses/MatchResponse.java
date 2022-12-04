package com.azad.wcstatapi.models.responses;

import com.azad.wcstatapi.models.Match;

public class MatchResponse extends Match {

    private Long id;

    public MatchResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
