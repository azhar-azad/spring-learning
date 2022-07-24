package com.azad.jsonplaceholderclone.models.dtos;

import com.azad.jsonplaceholderclone.models.Album;
import com.azad.jsonplaceholderclone.models.Member;

public class AlbumDto extends Album {

    private Long id;
    private Member member;
    private Long userId;

    public AlbumDto() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
