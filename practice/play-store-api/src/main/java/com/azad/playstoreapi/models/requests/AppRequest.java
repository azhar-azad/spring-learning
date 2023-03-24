package com.azad.playstoreapi.models.requests;

import com.azad.playstoreapi.models.pojos.App;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class AppRequest extends App {

    private Long id;

    @NotNull(message = "Publisher name must be provided")
    @Size(min = 2, message = "Minimum length of a publisher name must be 2 or more characters")
    private String pubName;

    @NotNull(message = "Category names must be provided")
    private List<String> categoryNames;

    public AppRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
