package com.azad.worldcup22api.models.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String groupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    private List<TeamEntity> teams;

    public GroupEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamEntity> teams) {
        this.teams = teams;
    }
}
