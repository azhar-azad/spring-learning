package com.azad.jsonplaceholderclone.models.responses;

import com.azad.jsonplaceholderclone.models.Member;
import com.azad.jsonplaceholderclone.models.MemberProfile;

public class MemberResponse extends Member {

    private Long id;

    private MemberProfile memberProfile;

    public MemberResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MemberProfile getMemberProfile() {
        return memberProfile;
    }

    public void setMemberProfile(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;
    }
}
