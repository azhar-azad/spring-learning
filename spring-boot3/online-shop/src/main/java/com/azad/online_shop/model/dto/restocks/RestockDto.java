package com.azad.online_shop.model.dto.restocks;

import com.azad.online_shop.model.pojo.Product;
import com.azad.online_shop.model.pojo.Restock;
import com.azad.online_shop.model.pojo.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RestockDto extends Restock {

    private Long id;
    private Long supplierId;
    private Supplier supplier;
    private Product product;
}
