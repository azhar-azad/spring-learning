package com.azad.wcstatapi.models.dtos;

import com.azad.wcstatapi.models.Group;

public class GroupDto extends Group {

    private Long id;

    public GroupDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
