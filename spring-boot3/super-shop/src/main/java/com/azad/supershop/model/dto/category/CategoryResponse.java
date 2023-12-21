package com.azad.supershop.model.dto.category;

import com.azad.supershop.model.pojo.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryResponse extends Category {

    private Long id;
}
