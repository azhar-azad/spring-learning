package com.azad.moneymanagerapi.models.responses;

import com.azad.moneymanagerapi.models.pojos.Category;
import com.azad.moneymanagerapi.models.pojos.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class SubcategoryResponse extends Subcategory {

    private Long id;
    private Long categoryId;
    private Category category;
}
