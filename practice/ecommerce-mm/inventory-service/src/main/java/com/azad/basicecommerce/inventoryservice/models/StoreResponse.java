package com.azad.basicecommerce.inventoryservice.models;

import com.azad.basicecommerce.auth.models.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoreResponse extends Store {

    private AppUser storeOwner;
}
