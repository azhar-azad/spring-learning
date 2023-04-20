package com.azad.moneymanagerapi.models.dtos;

import com.azad.moneymanagerapi.models.pojos.Account;
import com.azad.moneymanagerapi.models.pojos.AccountGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class AccountDto extends Account {

    private Long id;
    private String accountGroupName;
    private AccountGroup accountGroup;
}
