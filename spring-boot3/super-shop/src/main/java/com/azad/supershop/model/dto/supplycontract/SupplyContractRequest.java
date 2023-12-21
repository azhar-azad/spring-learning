package com.azad.supershop.model.dto.supplycontract;

import com.azad.supershop.model.pojo.Supplier;
import com.azad.supershop.model.pojo.SupplyContract;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SupplyContractRequest extends SupplyContract {

    private Long supplierId;
}
