package com.azad.cineplex2.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfo {

	private Long id;
	private String title;
	private String summary;
	private int releaseYear;
}
