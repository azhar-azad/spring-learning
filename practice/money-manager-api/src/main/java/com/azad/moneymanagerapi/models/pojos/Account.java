package com.azad.moneymanagerapi.models.pojos;

import com.azad.moneymanagerapi.commons.validations.EnumValidator;
import com.azad.moneymanagerapi.models.constants.CurrencyTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Account {

    @NotNull(message = "Account name cannot be empty.")
    protected String accountName;

    protected Double balance;

    @NotNull(message = "Currency cannot be empty. Supported currencies are AUD/BDT/CAD/EUR/GBP/INR/JPY/USD")
    @EnumValidator(enumClass = CurrencyTypes.class, message = "Currency not supported yet")
    protected String currency;

    protected String description;
}
