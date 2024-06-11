package com.azad.online_shop.model.dto.products;

import com.azad.online_shop.model.pojo.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDto extends Product {

    private Long id;
}
