package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.App;
import com.azad.playstoreapi.models.pojos.PlayStoreUser;
import com.azad.playstoreapi.models.pojos.UserRating;

public class UserRatingDto extends UserRating {

    private Long id;
    private App app;
    private PlayStoreUser user;

    public UserRatingDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public PlayStoreUser getUser() {
        return user;
    }

    public void setUser(PlayStoreUser user) {
        this.user = user;
    }
}
