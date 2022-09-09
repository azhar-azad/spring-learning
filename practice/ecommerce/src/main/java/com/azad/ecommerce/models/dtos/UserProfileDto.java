package com.azad.ecommerce.models.dtos;

import com.azad.ecommerce.models.UserProfile;

public class UserProfileDto extends UserProfile {

    private Long id;

    public UserProfileDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
