package com.azad.onlinecourse.models.subcategory;

import com.azad.onlinecourse.common.exception.ApiError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubcategoryResponse extends Subcategory {

    private Long id;
    private ApiError error;
}
