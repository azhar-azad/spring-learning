package com.azad.cineplex2.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePersonnelResponseBasic {

	protected Long id;
	protected String fullName;
	protected int age;
	protected String gender;
	protected String stageName;
}
