package com.azad.cineplex2.dto.responses;

import java.util.List;

import com.azad.cineplex2.dto.MovieInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponseBasic {

	protected Long id;
	protected String name;
}
