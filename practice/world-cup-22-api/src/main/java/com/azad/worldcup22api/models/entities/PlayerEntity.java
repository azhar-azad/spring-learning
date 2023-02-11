package com.azad.worldcup22api.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "jersey_no")
    private Integer jerseyNo;

    @Column(name = "age")
    private Integer age;

    @Column(name = "position")
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    public PlayerEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getJerseyNo() {
        return jerseyNo;
    }

    public void setJerseyNo(Integer jerseyNo) {
        this.jerseyNo = jerseyNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }
}
