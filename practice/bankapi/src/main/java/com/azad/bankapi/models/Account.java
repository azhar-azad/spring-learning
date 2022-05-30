package com.azad.bankapi.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@NotNull(message = "Account number cannot be null")
	@Min(100)
	@Max(999999999)
	private Long acctNumber;
	
	@NotNull(message = "Account type cannot be null")
	@Size(min = 1, max = 10)
	private String type;
	
	private String status;
}
