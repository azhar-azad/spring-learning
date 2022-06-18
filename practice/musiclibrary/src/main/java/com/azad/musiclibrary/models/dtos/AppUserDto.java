package com.azad.musiclibrary.models.dtos;

import com.azad.musiclibrary.models.AppUser;

public class AppUserDto extends AppUser {

    private Long id;

    public AppUserDto() {
    }

    public AppUserDto(String firstName, String lastName, String email, String username, String password, Long id) {
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
