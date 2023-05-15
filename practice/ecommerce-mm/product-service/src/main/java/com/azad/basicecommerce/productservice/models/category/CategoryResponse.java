package com.azad.basicecommerce.productservice.models.category;

import com.azad.basicecommerce.productservice.models.productline.ProductLine;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class CategoryResponse extends Category {

    private ProductLine productLine;
}
