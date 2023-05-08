package com.azad.ecommerce.inventoryservice.models.responses;

import com.azad.ecommerce.inventoryservice.models.pojos.Store;
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
