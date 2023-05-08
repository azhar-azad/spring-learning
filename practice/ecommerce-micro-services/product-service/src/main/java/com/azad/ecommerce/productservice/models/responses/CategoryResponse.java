package com.azad.ecommerce.productservice.models.responses;

import com.azad.ecommerce.productservice.models.pojos.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse extends Category {

    private Long id;
    private Long categoryUid;

    private Long productLineUid;
}
