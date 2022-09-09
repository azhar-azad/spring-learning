package com.azad.ecommerce.models.responses;

import com.azad.ecommerce.models.UserProfile;

public class UserProfileResponse extends UserProfile {

    private Long id;

    public UserProfileResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
