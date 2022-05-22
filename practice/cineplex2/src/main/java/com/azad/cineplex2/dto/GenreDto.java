package com.azad.cineplex2.dto;

import java.util.List;

import com.azad.cineplex2.entities.Genre;
import com.azad.cineplex2.entities.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

	private Long id;
	private String name;
	
	private List<Movie> movies;
	private List<String> movieTitles;
	private List<Long> movieIds;
}
