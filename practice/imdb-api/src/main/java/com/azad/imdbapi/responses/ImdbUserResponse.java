package com.azad.imdbapi.responses;

import com.azad.imdbapi.models.ImdbUser;

public class ImdbUserResponse extends ImdbUser {

    private Long id;

    public ImdbUserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
