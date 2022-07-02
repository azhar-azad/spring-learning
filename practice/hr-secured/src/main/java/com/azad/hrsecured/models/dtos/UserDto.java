package com.azad.hrsecured.models.dtos;

import com.azad.hrsecured.models.User;

public class UserDto extends User {

    private Long id;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
