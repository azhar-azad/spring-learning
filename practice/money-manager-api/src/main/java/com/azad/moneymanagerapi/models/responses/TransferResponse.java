package com.azad.moneymanagerapi.models.responses;

import com.azad.moneymanagerapi.models.pojos.Transfer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransferResponse extends Transfer {

    private Long id;
}
