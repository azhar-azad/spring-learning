package com.azad.wcstatapi.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wc_groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wc_group_id")
    private Long id;

    @Column(name = "wc_group_name", nullable = false, length = 1)
    private String wcGroupName;

    @Column(name = "wc_group_champion")
    private String wcGroupChampion;

    @Column(name = "wc_group_runners_up")
    private String wcGroupRunnersUp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    private List<TeamEntity> teams;

    public GroupEntity() {
    }

    public Long getId() {
        return id;
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

    public List<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamEntity> teams) {
        this.teams = teams;
    }
}
