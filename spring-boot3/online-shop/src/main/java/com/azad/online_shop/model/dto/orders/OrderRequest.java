package com.azad.online_shop.model.dto.orders;

import com.azad.online_shop.model.pojo.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderRequest extends Order {
}
