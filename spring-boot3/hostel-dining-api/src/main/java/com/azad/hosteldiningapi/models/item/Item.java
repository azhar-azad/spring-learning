package com.azad.hosteldiningapi.models.item;

import com.azad.hosteldiningapi.common.utils.ApiUtils;
import com.azad.hosteldiningapi.models.PojoModel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Item extends PojoModel {

    @NotNull(message = "Item name cannot be empty")
    protected String itemName;

    @NotNull(message = "Item price cannot be empty")
    protected Double price;

    protected String description;
    protected String imageUrl;

    @Override
    public String getUid() {
        return ApiUtils.generateItemUid(itemName, price);
    }
}
