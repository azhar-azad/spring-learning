package com.azad.cineplex2.dto.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreRequest {

	@NotNull(message = "Genre name cannot be empty")
	@Size(min = 1, max = 10, message = "Genre name has to be between 1 to 10 characters long")
	private String name;
}
