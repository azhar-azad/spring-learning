package com.azad.cineplex2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePersonnelInfo {

	private Long id;
	private String fullName;
	private int age;
	private String gender;
	private String stageName;
}
