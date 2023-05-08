package com.azad.ecommerce.inventoryservice.models.dtos;

import com.azad.ecommerce.inventoryservice.models.pojos.Store;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoreDto extends Store {

    private Long id;
    private String storeUid;
}
