package com.azad.playstoreapi.models.responses;

import com.azad.playstoreapi.models.pojos.App;
import com.azad.playstoreapi.models.pojos.Category;
import com.azad.playstoreapi.models.pojos.Publisher;
import com.azad.playstoreapi.models.pojos.RatingHistory;

import java.util.List;

public class AppResponse extends App {

    private Long id;

    private Publisher publisher;
    private List<Category> categories;
    private RatingHistory ratingHistory;

    public AppResponse() {
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

    public RatingHistory getRatingHistory() {
        return ratingHistory;
    }

    public void setRatingHistory(RatingHistory ratingHistory) {
        this.ratingHistory = ratingHistory;
    }
}
