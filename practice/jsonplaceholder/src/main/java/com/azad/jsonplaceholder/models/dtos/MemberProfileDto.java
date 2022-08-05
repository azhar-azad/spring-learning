package com.azad.jsonplaceholder.models.dtos;

import com.azad.jsonplaceholder.models.MemberProfile;

public class MemberProfileDto extends MemberProfile {

    private Long id;

    public MemberProfileDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
