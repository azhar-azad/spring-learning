package com.azad.ecommerce.models;

import javax.validation.constraints.NotNull;

public class Geo {
    @NotNull(message = "Latitude cannot be empty")
    private String lat;

    @NotNull(message = "Longitude cannot be empty")
    private String lng;

    public Geo() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
