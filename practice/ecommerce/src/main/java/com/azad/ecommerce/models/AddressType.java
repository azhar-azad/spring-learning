package com.azad.ecommerce.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressType {

    @NotNull(message = "Address type cannot be empty")
    @Size(min = 1, max = 50, message = "Address type must be between 1 and 50 characters")
    private String type;

    public AddressType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
