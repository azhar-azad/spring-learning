package com.azad.wcstatapi.models;

import com.azad.wcstatapi.models.constants.MatchStatus;
import com.azad.wcstatapi.models.constants.TournamentStage;
import com.azad.wcstatapi.validations.EnumValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Match {

    private Integer matchNo;

    @NotNull(message = "Match date cannot be empty (dd/MM/yyyy)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate matchDate;

    @NotNull(message = "Match round cannot be empty. GROUP_STAGE/SECOND_ROUND/QUARTER_FINAL/SEMI_FINAL/THIRD_PLACE_QUALIFIER/Final")
    @EnumValidator(enumClass = TournamentStage.class, message = "Not a valid round")
    private String round;

    @NotNull(message = "Match status cannot be empty. SCHEDULED/STARTED/FINISHED/POSTPONED/CANCELED")
    @EnumValidator(enumClass = MatchStatus.class, message = "Not a valid match status")
    private String status;

    @NotNull(message = "Team1 name cannot be empty")
    private String team1;

    @NotNull(message = "Team2 name cannot be empty")
    private String team2;

    @NotNull(message = "Match result cannot be empty (0-0)")
    @Size(min = 3, max = 3, message = "Result should be of length 3")
    private String result;

    @NotNull(message = "Mandatory filed isDraw: true/false")
    private Boolean draw;
    private String winner;
    private String looser;
    private Map<String, List<String>> goalScorers;
    private Map<String, List<String>> assistProviders;
    private Map<String, List<String>> gkSaves;

    public Match() {
    }

    public Integer getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(Integer matchNo) {
        this.matchNo = matchNo;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLooser() {
        return looser;
    }

    public void setLooser(String looser) {
        this.looser = looser;
    }

    public Boolean getDraw() {
        return draw;
    }

    public void setDraw(Boolean draw) {
        this.draw = draw;
    }

    public Map<String, List<String>> getGoalScorers() {
        return goalScorers;
    }

    public void setGoalScorers(Map<String, List<String>> goalScorers) {
        this.goalScorers = goalScorers;
    }

    public Map<String, List<String>> getAssistProviders() {
        return assistProviders;
    }

    public void setAssistProviders(Map<String, List<String>> assistProviders) {
        this.assistProviders = assistProviders;
    }

    public Map<String, List<String>> getGkSaves() {
        return gkSaves;
    }

    public void setGkSaves(Map<String, List<String>> gkSaves) {
        this.gkSaves = gkSaves;
    }
}
