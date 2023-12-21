package com.azad.supershop.model.dto.supplier;

import com.azad.supershop.model.pojo.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SupplierResponse extends Supplier {

    private Long id;
}
