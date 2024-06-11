package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Supplier {

    @NotBlank(message = "Supplier name cannot be null")
    private String supplierName;

    @NotBlank(message = "Supplier contact info cannot be null")
    private String contactInfo;
}
