package com.azad.worldcup22api.models.dtos;

import com.azad.worldcup22api.models.Group;

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
