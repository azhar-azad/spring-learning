package com.azad.musiclibrary.responses;

import com.azad.musiclibrary.models.AppUser;

public class AppUserResponse extends AppUser {

    private Long id;

    public AppUserResponse() {
    }

    public AppUserResponse(String firstName, String lastName, String email, String username, String password, Long id) {
        super(firstName, lastName, email, username, password);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
