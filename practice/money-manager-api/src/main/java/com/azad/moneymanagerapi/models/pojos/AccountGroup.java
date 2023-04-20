package com.azad.moneymanagerapi.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class AccountGroup {

    @NotNull(message = "Group name cannot be empty.")
    protected String accountGroupName;
}
