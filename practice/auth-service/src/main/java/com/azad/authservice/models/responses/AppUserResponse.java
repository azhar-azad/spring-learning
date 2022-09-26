package com.azad.authservice.models.responses;

import com.azad.authservice.models.pojos.AppUser;

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
