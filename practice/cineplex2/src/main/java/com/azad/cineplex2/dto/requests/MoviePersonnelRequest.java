package com.azad.cineplex2.dto.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePersonnelRequest {

	@NotNull(message = "Full name cannot be empty")
	@Size(min = 1, max = 30, message = "Full name has to be between 1 to 30 characters")
	private String fullName;
	
	@NotNull(message = "Age cannot be empty")
	@Min(1)
	private int age;
		
	@NotNull(message = "Gender cannot be empty")
	private String gender;
	
	private String stageName;
}
