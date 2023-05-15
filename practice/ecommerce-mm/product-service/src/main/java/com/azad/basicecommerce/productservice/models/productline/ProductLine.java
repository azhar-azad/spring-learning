package com.azad.basicecommerce.productservice.models.productline;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class ProductLine {

    protected String productLineUid;

    @NotNull(message = "Product Line name must be provided")
    protected String productLineName;
}
