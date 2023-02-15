package com.azad.playstoreapi.models.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_review")
public class UserReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_review_id")
    private Long id;

    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private AppEntity app;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private PlayStoreUserEntity user;

    public UserReviewEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
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
