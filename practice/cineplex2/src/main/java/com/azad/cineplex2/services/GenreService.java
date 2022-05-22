package com.azad.cineplex2.services;

import com.azad.cineplex2.dto.GenreDto;

public interface GenreService extends GenericService<GenreDto> {

	GenreDto getByName(String name);
}
