package com.azad.bankapi.models;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

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
