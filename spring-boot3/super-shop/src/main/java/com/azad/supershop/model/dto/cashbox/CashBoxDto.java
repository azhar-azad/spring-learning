package com.azad.supershop.model.dto.cashbox;

import com.azad.supershop.model.pojo.CashBox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CashBoxDto extends CashBox {

    private Integer id;
}


