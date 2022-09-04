package com.azad.ecommerce.models.responses;

import com.azad.ecommerce.models.User;

public class UserResponse extends User {

    private Long id;

    public UserResponse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
