package com.azad.basicecommerce.inventory.models;

import com.azad.basicecommerce.auth.models.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoreDto extends Store {

    private Long id;
    private AppUser storeOwner;
}
