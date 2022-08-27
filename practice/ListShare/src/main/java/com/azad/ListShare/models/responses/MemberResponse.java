package com.azad.ListShare.models.responses;

import com.azad.ListShare.models.Member;

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
