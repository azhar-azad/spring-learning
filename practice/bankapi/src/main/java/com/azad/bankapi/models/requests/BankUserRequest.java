package com.azad.bankapi.models.requests;

import javax.validation.constraints.NotNull;

import com.azad.bankapi.models.BankUser;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankUserRequest extends BankUser {

	private String house;
	
	private String road;
	
	@NotNull(message = "Area identifier cannot be empty")
	private String area;
	
	private String city;
	
	@NotNull(message = "Upazila name cannot be empty")
	private String upazila;
	
	@NotNull(message = "District name cannot be empty")
	private String district;
}
