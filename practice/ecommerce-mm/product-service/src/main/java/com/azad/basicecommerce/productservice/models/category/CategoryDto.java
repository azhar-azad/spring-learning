package com.azad.basicecommerce.productservice.models.category;

import com.azad.basicecommerce.productservice.models.productline.ProductLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto extends Category {

    private Long id;
    private String productLineUid;
    private ProductLine productLine;
}
