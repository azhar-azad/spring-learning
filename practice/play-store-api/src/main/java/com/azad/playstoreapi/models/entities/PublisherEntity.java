package com.azad.playstoreapi.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publisher")
public class PublisherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @Column(name = "publisher_name", nullable = false, unique = true, length = 50)
    private String pubName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<AppEntity> apps;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private PlayStoreUserEntity user;

    public PublisherEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public List<AppEntity> getApps() {
        return apps;
    }

    public void setApps(List<AppEntity> apps) {
        this.apps = apps;
    }

    public PlayStoreUserEntity getUser() {
        return user;
    }

    public void setUser(PlayStoreUserEntity user) {
        this.user = user;
    }
}
