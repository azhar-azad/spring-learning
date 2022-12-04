package com.azad.wcstatapi.models.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "matches",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"match_no"}), @UniqueConstraint(columnNames = { "round", "team_1", "team_2" }) })
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @Column(name = "match_no", unique = true, nullable = false)
    private int matchNo;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    @Column(name = "round", nullable = false)
    private String round;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "team_1", nullable = false)
    private String team1;

    @Column(name = "team_2", nullable = false)
    private String team2;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "winner")
    private String winner;

    @Column(name = "looser")
    private String looser;

    @Column(name = "is_draw")
    private Boolean draw;

    public MatchEntity() {
    }

    public Long getId() {
        return id;
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

    public void setMatchNo(int matchNo) {
        this.matchNo = matchNo;
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
}
