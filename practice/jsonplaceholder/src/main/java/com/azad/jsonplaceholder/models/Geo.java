package com.azad.jsonplaceholder.models;

import javax.validation.constraints.NotNull;

public class Geo {

    @NotNull(message = "Latitude cannot be empty")
    private double lat;

    @NotNull(message = "Longitude cannot be empty")
    private double lng;

    public Geo() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
