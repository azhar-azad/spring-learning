package com.azad.ListShare.models.dtos;

import com.azad.ListShare.models.CustomList;
import com.azad.ListShare.models.Member;

import java.util.List;

public class MemberDto extends Member {

    private Long id;
    private String roleName;
    private List<CustomList> lists;

    public MemberDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<CustomList> getLists() {
        return lists;
    }

    public void setLists(List<CustomList> lists) {
        this.lists = lists;
    }
}
