package com.azad.basicecommerce.productservice.models.product;

import com.azad.basicecommerce.inventoryservice.models.Store;
import com.azad.basicecommerce.productservice.models.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto extends Product {

    private Long id;
    private Store store;
    private Category category;

}
