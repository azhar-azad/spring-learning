package com.azad.playstoreapi.models.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Publisher {

    @NotNull(message = "Publisher name cannot be empty")
    @Size(min = 2, max = 50, message = "Publisher name length has to be between 2 to 50 characters.")
    protected String pubName;

    public Publisher() {
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }
}
