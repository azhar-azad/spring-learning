package com.azad.cineplex2.dto.responses;

import java.util.List;

import com.azad.cineplex2.dto.GenreInfo;
import com.azad.cineplex2.dto.MoviePersonnelInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

	private Long id;
	private String title;
	private String summary;
	private int releaseYear;
	
	private List<GenreInfo> genres; 
	
	private List<MoviePersonnelInfo> directors;
	
	private List<MoviePersonnelInfo> casts;
}
