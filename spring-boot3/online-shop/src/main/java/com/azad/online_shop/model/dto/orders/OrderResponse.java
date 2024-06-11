package com.azad.online_shop.model.dto.orders;

import com.azad.online_shop.model.pojo.Order;
import com.azad.online_shop.model.pojo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderResponse extends Order {

    private Long id;
    private User user;
}
