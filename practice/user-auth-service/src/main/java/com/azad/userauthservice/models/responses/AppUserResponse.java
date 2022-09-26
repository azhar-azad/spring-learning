package com.azad.userauthservice.models.responses;

import com.azad.userauthservice.models.AppUser;

public class AppUserResponse extends AppUser {

    private Long id;

    public AppUserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
