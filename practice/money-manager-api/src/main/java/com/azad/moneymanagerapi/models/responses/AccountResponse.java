package com.azad.moneymanagerapi.models.responses;

import com.azad.moneymanagerapi.models.pojos.Account;
import com.azad.moneymanagerapi.models.pojos.AccountGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountResponse extends Account {

    private Long id;
    private AccountGroup accountGroup;
}
