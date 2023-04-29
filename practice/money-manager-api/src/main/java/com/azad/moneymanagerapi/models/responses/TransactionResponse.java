package com.azad.moneymanagerapi.models.responses;

import com.azad.moneymanagerapi.models.pojos.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionResponse extends Transaction {

    private Long id;
    private String accountName;
    private String categoryName;
    private String subcategoryName;
}
