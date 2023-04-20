package com.azad.moneymanagerapi.models.pojos;

import com.azad.moneymanagerapi.commons.validations.EnumValidator;
import com.azad.moneymanagerapi.models.constants.CategoryType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Category {

    @NotNull(message = "Category name cannot be empty.")
    protected String categoryName;

    @NotNull(message = "Category Type cannot be empty. Valid values are INCOME/EXPENSE")
    @EnumValidator(enumClass = CategoryType.class, message = "Not a valid type")
    protected String type;
}
