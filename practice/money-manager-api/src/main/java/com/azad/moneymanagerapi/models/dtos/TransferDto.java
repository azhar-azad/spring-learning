package com.azad.moneymanagerapi.models.dtos;

import com.azad.moneymanagerapi.models.pojos.Transfer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TransferDto extends Transfer {

    private Long id;
}
