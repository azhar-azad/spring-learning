package com.azad.bankapi.models.dtos;

import com.azad.bankapi.models.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends Account {

	private Long acctId;
}
