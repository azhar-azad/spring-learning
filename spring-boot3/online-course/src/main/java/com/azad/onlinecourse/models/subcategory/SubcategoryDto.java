package com.azad.onlinecourse.models.subcategory;

import com.azad.onlinecourse.common.exception.ApiError;
import com.azad.onlinecourse.models.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubcategoryDto extends Subcategory {

    private Long id;
    private Category category;
    private ApiError error;
}
