package com.azad.worldcup22api.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "player_stats")
public class PlayerStatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_stat_id")
    private Long id;

    @Column(name = "match_played")
    private Integer matchPlayed;

    @Column(name = "goal_scored")
    private Integer goalScored;

    @Column(name = "assist_made")
    private Integer assistMade;

    @Column(name = "save_count")
    private Integer saveCount;

    @Column(name = "yellow_card")
    private Integer yellowCard;

    @Column(name = "red_card")
    private Integer redCard;

    @Column(name = "fouls_committed")
    private Integer foulsCommitted;

    public PlayerStatEntity() {
    }

    public Long getId() {
        return id;
    }

    public Integer getMatchPlayed() {
        return matchPlayed;
    }

    public void setMatchPlayed(Integer matchPlayed) {
        this.matchPlayed = matchPlayed;
    }

    public Integer getGoalScored() {
        return goalScored;
    }

    public void setGoalScored(Integer goalScored) {
        this.goalScored = goalScored;
    }

    public Integer getAssistMade() {
        return assistMade;
    }

    public void setAssistMade(Integer assistMade) {
        this.assistMade = assistMade;
    }

    public Integer getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Integer saveCount) {
        this.saveCount = saveCount;
    }

    public Integer getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(Integer yellowCard) {
        this.yellowCard = yellowCard;
    }

    public Integer getRedCard() {
        return redCard;
    }

    public void setRedCard(Integer redCard) {
        this.redCard = redCard;
    }

    public Integer getFoulsCommitted() {
        return foulsCommitted;
    }

    public void setFoulsCommitted(Integer foulsCommitted) {
        this.foulsCommitted = foulsCommitted;
    }
}
