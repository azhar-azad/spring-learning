package com.azad.cineplex2.dto.responses;

import java.util.List;

import com.azad.cineplex2.dto.MovieInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponse extends GenreResponseBasic {
	
	private List<MovieInfo> movies;
}
