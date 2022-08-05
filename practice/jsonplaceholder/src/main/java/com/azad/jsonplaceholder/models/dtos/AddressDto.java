package com.azad.jsonplaceholder.models.dtos;

import com.azad.jsonplaceholder.models.Address;
import com.azad.jsonplaceholder.models.Member;

public class AddressDto extends Address {

    private Long id;

    private Member member;

    public AddressDto() {
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
