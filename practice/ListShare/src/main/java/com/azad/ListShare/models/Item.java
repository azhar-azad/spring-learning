package com.azad.ListShare.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Item {

    @NotNull(message = "Item name cannot be empty.")
    @Size(min = 2, max = 30, message = "Item name length has to be between 2 to 30 characters.")
    private String itemName;

    @NotNull(message = "Item price cannot be empty.")
    @Min(0)
    private Double price;

    @NotNull(message = "Item description cannot be empty.")
    @Size(min = 2, message = "Item description should be more than 2 characters.")
    private String description;

    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
