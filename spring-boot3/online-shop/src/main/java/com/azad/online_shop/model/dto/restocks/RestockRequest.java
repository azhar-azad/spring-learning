package com.azad.online_shop.model.dto.restocks;

import com.azad.online_shop.model.pojo.Restock;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RestockRequest extends Restock {

    private Long supplierId;
}
