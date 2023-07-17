package com.azad.hosteldiningapi.models.item;

import com.azad.hosteldiningapi.common.exceptions.ApiError;
import com.azad.hosteldiningapi.models.ResponseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ItemResponse extends Item implements ResponseModel {

    private Long id;
    private ApiError error;

    @Override
    public ApiError getError() {
        return error;
    }
}
