package com.azad.cineplex2.services;

import com.azad.cineplex2.dto.HallDto;

public interface HallService extends GenericService<HallDto> {

	HallDto getByName(String name);
}
