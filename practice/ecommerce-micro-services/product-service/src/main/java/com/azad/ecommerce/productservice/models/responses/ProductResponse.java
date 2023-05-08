package com.azad.ecommerce.productservice.models.responses;

import com.azad.ecommerce.productservice.models.pojos.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductResponse extends Product {

    private Long id;
    private Long productUid;
    private Long categoryUid;
    private Long storeUid;
}
