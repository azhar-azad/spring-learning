package com.azad.supershop.model.dto.supplycontract;

import com.azad.supershop.model.pojo.Supplier;
import com.azad.supershop.model.pojo.SupplyContract;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SupplyContractDto extends SupplyContract {

    private Long id;

    private Long supplierId;
    private Supplier supplier;
}
