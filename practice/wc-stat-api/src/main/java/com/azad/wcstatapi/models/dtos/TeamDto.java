package com.azad.wcstatapi.models.dtos;

import com.azad.wcstatapi.models.Group;
import com.azad.wcstatapi.models.TeamData;
import com.azad.wcstatapi.models.Team;

public class TeamDto extends Team {

    private Long id;
    private String groupName;
    private Group group;
    private TeamData teamData;

    public TeamDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
