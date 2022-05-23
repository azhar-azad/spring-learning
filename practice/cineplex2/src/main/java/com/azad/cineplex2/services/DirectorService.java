package com.azad.cineplex2.services;

import com.azad.cineplex2.dto.MoviePersonnelDto;

public interface DirectorService extends GenericService<MoviePersonnelDto> {

	MoviePersonnelDto getByFullName(String directorFullName);

}
