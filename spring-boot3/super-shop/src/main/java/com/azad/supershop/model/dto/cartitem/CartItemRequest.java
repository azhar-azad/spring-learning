package com.azad.supershop.model.dto.cartitem;

import com.azad.supershop.model.pojo.Cart;
import com.azad.supershop.model.pojo.CartItem;
import com.azad.supershop.model.pojo.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CartItemRequest extends CartItem {

    private Long cartId;
    private Long productId;
}
