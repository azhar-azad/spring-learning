package com.azad.jsonplaceholder.models.responses;

import com.azad.jsonplaceholder.models.Member;

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
