package com.azad.basicecommerce.productservice.models.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest extends Category {

    private String productLineUid;
}
