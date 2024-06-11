package com.azad.online_shop.model.dto.inventories;

import com.azad.online_shop.model.pojo.Inventory;
import com.azad.online_shop.model.pojo.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryResponse extends Inventory {

    private Long id;
    private Product product;
}
