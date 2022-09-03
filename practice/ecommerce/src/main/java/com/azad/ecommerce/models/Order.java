package com.azad.ecommerce.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Order {

    @NotNull(message = "Item count cannot be empty")
    @Size(min = 1, max = 10, message = "Item count length has to be between 1 to 10 characters.")
    private String itemCount;

    @NotNull(message = "Total price cannot be empty")
    @Size(min = 1, max = 10, message = "Total price length has to be between 1 to 10 characters.")
    private String totalPrice;

    public Order() {
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
