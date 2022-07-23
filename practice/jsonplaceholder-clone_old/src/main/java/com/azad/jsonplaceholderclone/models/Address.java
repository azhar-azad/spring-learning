package com.azad.jsonplaceholderclone.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {

    private String street;
    private String suite;

    @NotNull(message = "City name cannot be empty")
    @Size(min = 2, max = 20, message = "City name length has to be in between 2 to 20 characters")
    private String city;
    private String zipcode;

    private Geo geo;

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
