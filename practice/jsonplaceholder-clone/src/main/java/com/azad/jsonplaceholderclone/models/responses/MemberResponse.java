package com.azad.jsonplaceholderclone.models.responses;

import com.azad.jsonplaceholderclone.models.Member;

public class MemberResponse extends Member {

    private Long id;

    public MemberResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
