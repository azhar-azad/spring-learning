package com.azad.jsonplaceholder.models.responses;

import com.azad.jsonplaceholder.models.MemberProfile;

public class MemberProfileResponse extends MemberProfile {

    private Long id;

    public MemberProfileResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
