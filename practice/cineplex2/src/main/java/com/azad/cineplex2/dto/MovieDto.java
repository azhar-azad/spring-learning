package com.azad.cineplex2.dto;

import java.util.List;

import com.azad.cineplex2.entities.Director;
import com.azad.cineplex2.entities.Genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

	private Long id;
	private String title;
	private String summary;
	private int releaseYear;
	
	private List<Genre> genres;
	private List<String> genreNames;
	private List<Long> genreIds;
	
	private List<Director> directors;
	private List<String> directorFullNames;
	private List<Long> directorIds;
}
