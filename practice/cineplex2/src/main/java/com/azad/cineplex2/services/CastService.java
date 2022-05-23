package com.azad.cineplex2.services;

import com.azad.cineplex2.dto.MoviePersonnelDto;

public interface CastService extends GenericService<MoviePersonnelDto> {

	MoviePersonnelDto getByFullName(String castFullName);

}
