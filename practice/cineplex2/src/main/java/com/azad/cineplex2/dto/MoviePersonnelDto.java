package com.azad.cineplex2.dto;

import java.util.Date;
import java.util.List;

import com.azad.cineplex2.entities.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePersonnelDto {

	private Long id;
	private String fullName;
	protected Date birthDate;
	protected Boolean isDead;
	protected Date deathDate;
	private int age;
	private String gender;
	private String stageName;
	
	private List<Movie> movies;
	private List<String> movieTitles;
	private List<Long> movieIds;
}
