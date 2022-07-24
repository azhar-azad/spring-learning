package com.azad.jsonplaceholderclone.models.responses;

import com.azad.jsonplaceholderclone.models.Member;
import com.azad.jsonplaceholderclone.models.Todo;

public class TodoResponse extends Todo {

    private Long id;
    private Long userId;

//    private Member member;

    public TodoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Member getMember() {
//        return member;
//    }
//
//    public void setMember(Member member) {
//        this.member = member;
//    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
