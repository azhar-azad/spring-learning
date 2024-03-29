package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.App;
import com.azad.playstoreapi.models.pojos.PlayStoreUser;
import com.azad.playstoreapi.models.pojos.Publisher;

import java.util.List;

public class PublisherDto extends Publisher {

    private Long id;
    private List<App> apps;
    private PlayStoreUser user;

    public PublisherDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    public PlayStoreUser getUser() {
        return user;
    }

    public void setUser(PlayStoreUser user) {
        this.user = user;
    }
}
