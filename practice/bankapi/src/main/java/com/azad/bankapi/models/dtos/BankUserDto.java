package com.azad.bankapi.models.dtos;

import com.azad.bankapi.models.Address;
import com.azad.bankapi.models.BankUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankUserDto extends BankUser {

	private Long bankUserId;
	private Address address;
}
