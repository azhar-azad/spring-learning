package com.azad.musiclibrary.models.requests;

import com.azad.musiclibrary.models.AppUser;

public class AppUserRequest extends AppUser {

    public AppUserRequest() {
    }

    public AppUserRequest(String firstName, String lastName, String email, String username, String password) {
        super(firstName, lastName, email, username, password);
    }
}
