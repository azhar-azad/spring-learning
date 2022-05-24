package com.azad.estatement.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

	@NotNull(message = "Organization unique name cannot be empty")
	@Size(min = 4, max = 4)
	protected String orgUniquename;
	
	@NotNull(message = "Organization display name cannot be empty")
	@Size(min = 4, max = 30)
	protected String orgDisplayname;
	
	protected String orgServerName;
	
	protected String schemaName;
}
