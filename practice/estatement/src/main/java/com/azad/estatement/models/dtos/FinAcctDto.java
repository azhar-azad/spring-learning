package com.azad.estatement.models.dtos;

import com.azad.estatement.models.FinAccount;
import com.azad.estatement.models.Organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinAcctDto extends FinAccount {

	private Long acctId;
	
	private Organization org;
	private Long orgId;
}
