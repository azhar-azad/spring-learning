package com.azad.onlinecourse.models.category;

import com.azad.onlinecourse.common.exception.ApiError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse extends Category {

    private Long id;
    private ApiError error;
}
