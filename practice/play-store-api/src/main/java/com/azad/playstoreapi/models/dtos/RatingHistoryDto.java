package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.App;
import com.azad.playstoreapi.models.pojos.RatingHistory;

public class RatingHistoryDto extends RatingHistory {

    private Long id;
    private App app;
    private Long appId;

    public RatingHistoryDto() {
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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
