package com.azad.playstoreapi.models.pojos;

public class RatingHistory {

    protected Double currentRating;
    protected Integer highestRating;
    protected Integer lowestRating;
    protected Long totalRatingCount;
    protected Long fiveStarRatingCount;
    protected Long fourStarRatingCount;
    protected Long threeStarRatingCount;
    protected Long twoStarRatingCount;
    protected Long oneStarRatingCount;

    public RatingHistory() {
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
}
