package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.App;
import com.azad.playstoreapi.models.pojos.Category;
import com.azad.playstoreapi.models.pojos.Publisher;
import com.azad.playstoreapi.models.pojos.RatingHistory;

import java.util.List;

public class AppDto extends App {

    private Long id;

    private Publisher publisher;
    private String pubName;
    private List<Category> categories;
    private List<String> categoryNames;
    private RatingHistory ratingHistory;

    public AppDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public RatingHistory getRatingHistory() {
        return ratingHistory;
    }

    public void setRatingHistory(RatingHistory ratingHistory) {
        this.ratingHistory = ratingHistory;
    }
}
