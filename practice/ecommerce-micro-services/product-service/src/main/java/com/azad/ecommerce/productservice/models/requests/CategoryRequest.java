package com.azad.ecommerce.productservice.models.requests;

import com.azad.ecommerce.productservice.models.pojos.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest extends Category {

    private Long productLineUid;
}
