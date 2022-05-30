package com.azad.bankapi.models.responses;

import com.azad.bankapi.models.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse extends Account {

	private Long acctId;
}
