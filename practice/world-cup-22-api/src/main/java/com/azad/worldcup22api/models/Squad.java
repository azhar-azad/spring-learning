package com.azad.worldcup22api.models;

import java.util.List;

public class Squad {

    private List<Player> startingPlayers;
    private List<Player> subPlayers;

    public Squad() {
    }

    public List<Player> getStartingPlayers() {
        return startingPlayers;
    }

    public void setStartingPlayers(List<Player> startingPlayers) {
        this.startingPlayers = startingPlayers;
    }

    public List<Player> getSubPlayers() {
        return subPlayers;
    }

    public void setSubPlayers(List<Player> subPlayers) {
        this.subPlayers = subPlayers;
    }
}
