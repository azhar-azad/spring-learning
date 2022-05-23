package com.azad.cineplex2.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePersonnelInfo {

	private Long id;
	private String fullName;
	protected Date birthDate;
	protected Boolean isDead;
	protected Date deathDate;
	private int age;
	private String gender;
	private String stageName;
}
