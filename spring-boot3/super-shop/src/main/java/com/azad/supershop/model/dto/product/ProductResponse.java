package com.azad.supershop.model.dto.product;

import com.azad.supershop.model.pojo.Category;
import com.azad.supershop.model.pojo.Product;
import com.azad.supershop.model.pojo.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductResponse extends Product {

    private Long id;
    private Category category;
    private Supplier supplier;
}
