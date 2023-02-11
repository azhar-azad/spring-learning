package com.azad.worldcup22api.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class Event {

    private String matchEvent;
    private LocalTime eventTime;
    private LocalDateTime matchScheduledTime; // MATCH_SCHEDULED
    private LocalTime matchStartTime; // MATCH_STARTED
    private LocalTime matchEndTime; // MATCH_END
    private Map<String, Squad> declaredSquad; // team-name - team-squad | SQUAD_DECLARED
    private Map<Player, Player> subPlayers; // player-subbed - player-replaced | SUBSTITUTION
    private String goalScoredBy; // GOAL, PENALTY/FREE_KICK(if scored)
    private String goalAssistedBy; // GOAL
    private String shotSavedBy; // SAVE, PENALTY/FREE_KICK(if saved)
    private String foulCommittedBy; // FOUL, YELLOW_CARD/RED_CARD, PENALTY(if in d-box), FREE_KICK
    private String penaltyTakenBy; // PENALTY
    private String freeKickTakenBy; // FREE_KICK
    private String yellowCardShownTo; // YELLOW_CARD
    private String redCardShownTo; // RED_CARD

    public Event() {
    }

    public String getMatchEvent() {
        return matchEvent;
    }

    public void setMatchEvent(String matchEvent) {
        this.matchEvent = matchEvent;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public LocalDateTime getMatchScheduledTime() {
        return matchScheduledTime;
    }

    public void setMatchScheduledTime(LocalDateTime matchScheduledTime) {
        this.matchScheduledTime = matchScheduledTime;
    }

    public LocalTime getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(LocalTime matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public LocalTime getMatchEndTime() {
        return matchEndTime;
    }

    public void setMatchEndTime(LocalTime matchEndTime) {
        this.matchEndTime = matchEndTime;
    }

    public Map<String, Squad> getDeclaredSquad() {
        return declaredSquad;
    }

    public void setDeclaredSquad(Map<String, Squad> declaredSquad) {
        this.declaredSquad = declaredSquad;
    }

    public Map<Player, Player> getSubPlayers() {
        return subPlayers;
    }

    public void setSubPlayers(Map<Player, Player> subPlayers) {
        this.subPlayers = subPlayers;
    }

    public String getGoalScoredBy() {
        return goalScoredBy;
    }

    public void setGoalScoredBy(String goalScoredBy) {
        this.goalScoredBy = goalScoredBy;
    }

    public String getGoalAssistedBy() {
        return goalAssistedBy;
    }

    public void setGoalAssistedBy(String goalAssistedBy) {
        this.goalAssistedBy = goalAssistedBy;
    }

    public String getShotSavedBy() {
        return shotSavedBy;
    }

    public void setShotSavedBy(String shotSavedBy) {
        this.shotSavedBy = shotSavedBy;
    }

    public String getFoulCommittedBy() {
        return foulCommittedBy;
    }

    public void setFoulCommittedBy(String foulCommittedBy) {
        this.foulCommittedBy = foulCommittedBy;
    }

    public String getPenaltyTakenBy() {
        return penaltyTakenBy;
    }

    public void setPenaltyTakenBy(String penaltyTakenBy) {
        this.penaltyTakenBy = penaltyTakenBy;
    }

    public String getFreeKickTakenBy() {
        return freeKickTakenBy;
    }

    public void setFreeKickTakenBy(String freeKickTakenBy) {
        this.freeKickTakenBy = freeKickTakenBy;
    }

    public String getYellowCardShownTo() {
        return yellowCardShownTo;
    }

    public void setYellowCardShownTo(String yellowCardShownTo) {
        this.yellowCardShownTo = yellowCardShownTo;
    }

    public String getRedCardShownTo() {
        return redCardShownTo;
    }

    public void setRedCardShownTo(String redCardShownTo) {
        this.redCardShownTo = redCardShownTo;
    }
}
