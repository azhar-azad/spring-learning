package com.azad.online_shop.model.pojo;

import com.azad.online_shop.model.contant.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Address {

    @NotBlank(message = "Address type cannot be blank. Should be Shipping/Billing")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String houseNo;
    private String roadNo;
    private String area;
    private String subDistrict;
    private String district;
    private String division;
}
