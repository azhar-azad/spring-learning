package com.azad.wcstatapi.models.requests;

import com.azad.wcstatapi.models.Team;

public class TeamRequest extends Team {

    private String groupName;

    public TeamRequest() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
