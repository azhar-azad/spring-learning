package com.azad.wcstatapi.models.responses;

import com.azad.wcstatapi.models.Group;
import com.azad.wcstatapi.models.TeamData;
import com.azad.wcstatapi.models.Team;

public class TeamResponse extends Team {

    private Long id;
    private Group group;
    private TeamData teamData;

    public TeamResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public TeamData getTeamData() {
        return teamData;
    }

    public void setTeamData(TeamData teamData) {
        this.teamData = teamData;
    }
}
