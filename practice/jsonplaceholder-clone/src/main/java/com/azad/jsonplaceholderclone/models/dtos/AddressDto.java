package com.azad.jsonplaceholderclone.models.dtos;

import com.azad.jsonplaceholderclone.models.Address;
import com.azad.jsonplaceholderclone.models.Geo;
import com.azad.jsonplaceholderclone.models.Member;

public class AddressDto extends Address {

    private Long id;
    private Member member;
    private Geo geo;

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

    @Override
    public Geo getGeo() {
        return geo;
    }

    @Override
    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
