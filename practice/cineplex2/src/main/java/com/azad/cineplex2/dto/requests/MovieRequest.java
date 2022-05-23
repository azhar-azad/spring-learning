package com.azad.cineplex2.dto.requests;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

	@NotNull(message = "Movie title cannot be empty")
	@Size(min = 1, max = 30, message = "Movie title has to be 1 to 30 characters long")
	private String title;
	
	@NotNull(message = "Movie summary cannot be empty")
	@Size(min = 2, max = 200, message = "Movie summary has to be 1 to 200 characters long")
	private String summary;
	
	@NotNull(message = "Release year cannot be empty")
	@Min(1700)
	@Max(2099)
	private int releaseYear;
	
	private List<String> genreNames;
	private List<Long> genreIds;
	
	private List<String> directorFullNames;
	private List<Long> directorIds;
}
