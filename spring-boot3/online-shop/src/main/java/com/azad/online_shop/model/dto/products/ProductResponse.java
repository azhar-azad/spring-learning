package com.azad.online_shop.model.dto.products;

import com.azad.online_shop.model.pojo.Inventory;
import com.azad.online_shop.model.pojo.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductResponse extends Product {

    private Long id;
    private Inventory inventory;
}
