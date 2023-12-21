package com.azad.supershop.model.dto.customer;

import com.azad.supershop.model.pojo.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerResponse extends Customer {

    private Long id;
}
