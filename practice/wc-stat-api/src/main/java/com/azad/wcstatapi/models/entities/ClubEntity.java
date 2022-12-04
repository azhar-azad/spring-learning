package com.azad.wcstatapi.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clubs")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "club_name", nullable = false)
    private String clubName;

    @Column(name = "league", nullable = false)
    private String league;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club", cascade = CascadeType.ALL)
    private List<PlayerEntity> players;

    public ClubEntity() {
    }

    public Long getId() {
        return id;
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

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }
}
