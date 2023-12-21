package com.azad.supershop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Supplier {

    @NotBlank
    protected String supplierName;

    @NotBlank
    protected String phone;
}
