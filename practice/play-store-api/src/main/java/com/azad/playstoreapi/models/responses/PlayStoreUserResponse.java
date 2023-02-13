package com.azad.playstoreapi.models.responses;

import com.azad.playstoreapi.models.pojos.PlayStoreUser;

public class PlayStoreUserResponse extends PlayStoreUser {

    private Long id;

    public PlayStoreUserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
