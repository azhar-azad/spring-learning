package com.azad.moneymanagerapi.models.requests;

import com.azad.moneymanagerapi.models.pojos.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class TransactionRequest extends Transaction {

    @NotNull(message = "Account name cannot be empty")
    private String accountName;

    @NotNull(message = "Category name cannot be empty")
    private String categoryName;

    private String subcategoryName;
}
