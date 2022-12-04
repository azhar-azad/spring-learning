package com.azad.wcstatapi.models;

import javax.validation.constraints.NotNull;

public class Club {

    @NotNull(message = "Club name cannot be empty")
    private String clubName;

    @NotNull(message = "League name cannot be empty")
    private String league;

    public Club() {
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
