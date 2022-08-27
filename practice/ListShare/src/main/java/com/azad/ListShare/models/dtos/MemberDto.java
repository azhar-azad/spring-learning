package com.azad.ListShare.models.dtos;

import com.azad.ListShare.models.Member;

public class MemberDto extends Member {

    private Long id;
    private String roleName;

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
}
