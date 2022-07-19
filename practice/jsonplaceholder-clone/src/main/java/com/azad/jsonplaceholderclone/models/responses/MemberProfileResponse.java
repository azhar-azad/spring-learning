package com.azad.jsonplaceholderclone.models.responses;

import com.azad.jsonplaceholderclone.models.Address;
import com.azad.jsonplaceholderclone.models.Company;
import com.azad.jsonplaceholderclone.models.Member;
import com.azad.jsonplaceholderclone.models.MemberProfile;

public class MemberProfileResponse extends MemberProfile {

    private Long id;

    private Member member;

    public MemberProfileResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
