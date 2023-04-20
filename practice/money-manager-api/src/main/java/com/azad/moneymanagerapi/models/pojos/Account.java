package com.azad.moneymanagerapi.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class Account {

    @NotNull(message = "Account name cannot be empty.")
    protected String accountName;

    protected Double balance;

    @Size(max = 3)
    protected String currency;

    protected String description;
}
