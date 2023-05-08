package com.azad.ecommerce.app.apis.inventory.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoreResponse extends Store {

    private Long id;
    private String storeUid;
}
