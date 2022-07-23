package com.azad.jsonplaceholderclone.models.dtos;

import com.azad.jsonplaceholderclone.models.Company;
import com.azad.jsonplaceholderclone.models.Member;

public class CompanyDto extends Company {

    private Long id;

    private Member member;

    public CompanyDto() {
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
