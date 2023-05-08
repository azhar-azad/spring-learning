package com.azad.ecommerce.productservice.models.requests;

import com.azad.ecommerce.productservice.models.pojos.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductRequest extends Product {

    private Long categoryUid;
    private Long storeUID;
}
