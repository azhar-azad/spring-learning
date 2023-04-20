package com.azad.moneymanagerapi.models.dtos;

import com.azad.moneymanagerapi.models.pojos.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CategoryDto extends Category {

    private Long id;
}
