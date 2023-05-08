package com.azad.ecommerce.productservice.models.responses;

import com.azad.ecommerce.productservice.models.pojos.ProductLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductLineResponse extends ProductLine {

    private Long id;
    private Long productLineUid;
}
