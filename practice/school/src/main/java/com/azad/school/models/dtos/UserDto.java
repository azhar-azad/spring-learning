package com.azad.school.models.dtos;

import com.azad.school.models.User;

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
