package com.azad.estatement.models.responses;

import com.azad.estatement.models.FinAccount;
import com.azad.estatement.models.Organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinAcctResponse extends FinAccount {

	private Long acctId;
	private Organization org;
}
