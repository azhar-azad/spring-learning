package com.azad.hosteldiningapi.models.item;

import com.azad.hosteldiningapi.models.DtoModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ItemDto extends Item implements DtoModel {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }
}
