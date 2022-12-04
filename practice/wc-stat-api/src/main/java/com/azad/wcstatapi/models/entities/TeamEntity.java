package com.azad.wcstatapi.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "fifa_ranking")
    private Integer fifaRanking;

    @Column(name = "coach")
    private String coach;

    @Column(name = "points")
    private Integer points;

    @Column(name = "group_match_played")
    private Integer groupMatchPlayed;

    @Column(name = "group_goal_scored")
    private Integer groupGoalScored;

    @Column(name = "group_goal_conceded")
    private Integer groupGoalConceded;

    @Column(name = "tournament_status")
    private String tournamentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @OneToOne(mappedBy = "team")
    private TeamDataEntity teamData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    private List<PlayerEntity> players;

    public TeamEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getFifaRanking() {
        return fifaRanking;
    }

    public void setFifaRanking(Integer fifaRanking) {
        this.fifaRanking = fifaRanking;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public Integer getGroupMatchPlayed() {
        return groupMatchPlayed;
    }

    public void setGroupMatchPlayed(Integer groupMatchPlayed) {
        this.groupMatchPlayed = groupMatchPlayed;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getGroupGoalScored() {
        return groupGoalScored;
    }

    public void setGroupGoalScored(Integer groupGoalScored) {
        this.groupGoalScored = groupGoalScored;
    }

    public Integer getGroupGoalConceded() {
        return groupGoalConceded;
    }

    public void setGroupGoalConceded(Integer groupGoalConceded) {
        this.groupGoalConceded = groupGoalConceded;
    }

    public String getTournamentStatus() {
        return tournamentStatus;
    }

    public void setTournamentStatus(String tournamentStatus) {
        this.tournamentStatus = tournamentStatus;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public TeamDataEntity getTeamData() {
        return teamData;
    }

    public void setTeamData(TeamDataEntity teamData) {
        this.teamData = teamData;
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }
}
