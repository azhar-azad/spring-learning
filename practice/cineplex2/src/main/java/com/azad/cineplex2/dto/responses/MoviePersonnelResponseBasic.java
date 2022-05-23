package com.azad.cineplex2.dto.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePersonnelResponseBasic {

	protected Long id;
	protected String fullName;
	protected Date birthDate;
	protected Boolean isDead;
	protected Date deathDate;
	protected int age;
	protected String gender;
	protected String stageName;
}
