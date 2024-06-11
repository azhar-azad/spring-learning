package com.azad.online_shop.model.dto.orders;

import com.azad.online_shop.model.pojo.OrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemResponse extends OrderItem {

    private Long id;
}
