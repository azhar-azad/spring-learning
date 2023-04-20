package com.azad.moneymanagerapi.models.responses;

import com.azad.moneymanagerapi.models.pojos.Account;
import com.azad.moneymanagerapi.models.pojos.AccountGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AccountGroupResponse extends AccountGroup {

    private Long id;
    private List<Account> accounts;
}
