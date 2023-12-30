package com.azad.supershop.model.dto.cart;

import com.azad.supershop.model.pojo.Cart;
import com.azad.supershop.model.pojo.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CartResponse extends Cart {

    private Long id;
    private Customer customer;
}
