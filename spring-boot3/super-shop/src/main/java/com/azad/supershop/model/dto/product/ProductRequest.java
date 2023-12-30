package com.azad.supershop.model.dto.product;

import com.azad.supershop.model.pojo.Category;
import com.azad.supershop.model.pojo.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductRequest extends Product {

    private Long categoryId;
    private Long supplierId;
}
