package com.azad.playstoreapi.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_rating")
public class UserRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_rating_id")
    private Long id;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private AppEntity app;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private PlayStoreUserEntity user;

    public UserRatingEntity() {
    }

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public AppEntity getApp() {
        return app;
    }

    public void setApp(AppEntity app) {
        this.app = app;
    }

    public PlayStoreUserEntity getUser() {
        return user;
    }

    public void setUser(PlayStoreUserEntity user) {
        this.user = user;
    }
}
