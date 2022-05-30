package com.azad.bankapi.models.responses;

import com.azad.bankapi.models.Address;
import com.azad.bankapi.models.BankUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankUserResponse extends BankUser {

	private Long bankUserId;
	private Address address;
}
