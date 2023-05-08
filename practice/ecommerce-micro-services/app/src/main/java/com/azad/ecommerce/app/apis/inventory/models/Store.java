package com.azad.ecommerce.app.apis.inventory.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Store {

    @NotNull(message = "Store name cannot be empty")
    protected String storeName;

    protected String pictureUrl;
    protected Double discount;
}
