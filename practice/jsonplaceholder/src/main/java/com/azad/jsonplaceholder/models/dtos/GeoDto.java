package com.azad.jsonplaceholder.models.dtos;

import com.azad.jsonplaceholder.models.Address;
import com.azad.jsonplaceholder.models.Geo;

public class GeoDto extends Geo {

    private Long id;

    private Address address;

    public GeoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
