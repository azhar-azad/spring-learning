package com.azad.playstoreapi.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app")
public class AppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long id;

    @Column(name = "app_name", nullable = false, length = 30)
    private String appName;

    @Column(name = "app_short_name")
    private String appShortName;

    @Column(name = "app_logo")
    private String appLogo;

    @Column(name = "app_description")
    private String appDescription;

    @Column(name = "app_size_mb", nullable = false)
    private Double appSizeMB;

    @Column(name = "pg_rating", nullable = false)
    private String pgRating;

    @Column(name = "download_count")
    private String downloadCount;

    @Column(name = "current_rating")
    private Double currentRating;

    @Column(name = "is_game")
    private String isGame;

    @ManyToMany
    @JoinTable(
            name = "app_category",
            joinColumns = @JoinColumn(name = "app_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categories;

    @ManyToOne(fetch = FetchType.LAZY)
    private PublisherEntity publisher;

    @OneToOne(mappedBy = "app")
    private RatingHistoryEntity ratingHistory;

    @OneToOne(mappedBy = "app")
    private UserRatingEntity userRating;

    @OneToOne(mappedBy = "app")
    private UserReviewEntity userReview;

    public AppEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppShortName() {
        return appShortName;
    }

    public void setAppShortName(String appShortName) {
        this.appShortName = appShortName;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getPgRating() {
        return pgRating;
    }

    public void setPgRating(String pgRating) {
        this.pgRating = pgRating;
    }

    public Double getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(Double currentRating) {
        this.currentRating = currentRating;
    }

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public RatingHistoryEntity getRatingHistory() {
        return ratingHistory;
    }

    public void setRatingHistory(RatingHistoryEntity ratingHistory) {
        this.ratingHistory = ratingHistory;
    }

    public UserRatingEntity getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRatingEntity userRating) {
        this.userRating = userRating;
    }

    public UserReviewEntity getUserReview() {
        return userReview;
    }

    public void setUserReview(UserReviewEntity userReview) {
        this.userReview = userReview;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public Double getAppSizeMB() {
        return appSizeMB;
    }

    public void setAppSizeMB(Double appSizeMB) {
        this.appSizeMB = appSizeMB;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getIsGame() {
        return isGame;
    }

    public void setIsGame(String isGame) {
        this.isGame = isGame;
    }
}
