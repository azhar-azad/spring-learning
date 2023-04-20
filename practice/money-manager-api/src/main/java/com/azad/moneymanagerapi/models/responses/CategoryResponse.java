package com.azad.moneymanagerapi.models.responses;

import com.azad.moneymanagerapi.models.pojos.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CategoryResponse extends Category {

    private Long id;
}
