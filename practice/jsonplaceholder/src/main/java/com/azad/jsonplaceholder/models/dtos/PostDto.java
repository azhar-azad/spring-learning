package com.azad.jsonplaceholder.models.dtos;

import com.azad.jsonplaceholder.models.Member;
import com.azad.jsonplaceholder.models.Post;

public class PostDto extends Post {

    private Long id;
    private Member member;
    private Long userId;

    public PostDto() {
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
