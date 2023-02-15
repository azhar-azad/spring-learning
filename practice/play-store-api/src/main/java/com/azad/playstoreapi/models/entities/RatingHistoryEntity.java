package com.azad.playstoreapi.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "rating_history")
public class RatingHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_history_id")
    private Long id;

    @Column(name = "current_rating")
    private Double currentRating;

    @Column(name = "highest_rating")
    private Integer highestRating;

    @Column(name = "lowest_rating")
    private Integer lowestRating;

    @Column(name = "total_rating")
    private Long totalRatingCount;

    @Column(name = "five_star")
    private Long fiveStarRatingCount;

    @Column(name = "four_star")
    private Long fourStarRatingCount;

    @Column(name = "three_star")
    private Long threeStarRatingCount;

    @Column(name = "two_star")
    private Long twoStarRatingCount;

    @Column(name = "one_star")
    private Long oneStarRatingCount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private AppEntity app;

    public RatingHistoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public Double getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(Double currentRating) {
        this.currentRating = currentRating;
    }

    public Integer getHighestRating() {
        return highestRating;
    }

    public void setHighestRating(Integer highestRating) {
        this.highestRating = highestRating;
    }

    public Integer getLowestRating() {
        return lowestRating;
    }

    public void setLowestRating(Integer lowestRating) {
        this.lowestRating = lowestRating;
    }

    public Long getTotalRatingCount() {
        return totalRatingCount;
    }

    public void setTotalRatingCount(Long totalRatingCount) {
        this.totalRatingCount = totalRatingCount;
    }

    public Long getFiveStarRatingCount() {
        return fiveStarRatingCount;
    }

    public void setFiveStarRatingCount(Long fiveStarRatingCount) {
        this.fiveStarRatingCount = fiveStarRatingCount;
    }

    public Long getFourStarRatingCount() {
        return fourStarRatingCount;
    }

    public void setFourStarRatingCount(Long fourStarRatingCount) {
        this.fourStarRatingCount = fourStarRatingCount;
    }

    public Long getThreeStarRatingCount() {
        return threeStarRatingCount;
    }

    public void setThreeStarRatingCount(Long threeStarRatingCount) {
        this.threeStarRatingCount = threeStarRatingCount;
    }

    public Long getTwoStarRatingCount() {
        return twoStarRatingCount;
    }

    public void setTwoStarRatingCount(Long twoStarRatingCount) {
        this.twoStarRatingCount = twoStarRatingCount;
    }

    public Long getOneStarRatingCount() {
        return oneStarRatingCount;
    }

    public void setOneStarRatingCount(Long oneStarRatingCount) {
        this.oneStarRatingCount = oneStarRatingCount;
    }

    public AppEntity getApp() {
        return app;
    }

    public void setApp(AppEntity app) {
        this.app = app;
    }
}
