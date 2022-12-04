package com.azad.wcstatapi.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Group {

    @NotNull(message = "Group name cannot be empty")
    @Size(min = 1, max = 1)
    private String wcGroupName;

    private String wcGroupChampion;
    private String wcGroupRunnersUp;

    public Group() {
    }

    public String getWcGroupName() {
        return wcGroupName;
    }

    public void setWcGroupName(String wcGroupName) {
        this.wcGroupName = wcGroupName;
    }

    public String getWcGroupChampion() {
        return wcGroupChampion;
    }

    public void setWcGroupChampion(String wcGroupChampion) {
        this.wcGroupChampion = wcGroupChampion;
    }

    public String getWcGroupRunnersUp() {
        return wcGroupRunnersUp;
    }

    public void setWcGroupRunnersUp(String wcGroupRunnersUp) {
        this.wcGroupRunnersUp = wcGroupRunnersUp;
    }
}
