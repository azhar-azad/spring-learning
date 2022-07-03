package com.azad.school.models.dtos;

import com.azad.school.models.User;

public class UserDto extends User {

    private Long userId;

    public UserDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
