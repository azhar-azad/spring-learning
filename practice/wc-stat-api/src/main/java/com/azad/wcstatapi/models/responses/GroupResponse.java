package com.azad.wcstatapi.models.responses;

import com.azad.wcstatapi.models.Group;

public class GroupResponse extends Group {

    private Long id;

    public GroupResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
