package com.azad.ListShare.models.dtos;

import com.azad.ListShare.models.CustomList;
import com.azad.ListShare.models.Item;
import com.azad.ListShare.models.Member;

import java.util.List;

public class CustomListDto extends CustomList {

    private Long id;
    private Member member;
    private Long userId;
    private List<Item> items;

    public CustomListDto() {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
