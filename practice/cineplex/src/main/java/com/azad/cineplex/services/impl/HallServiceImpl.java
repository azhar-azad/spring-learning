package com.azad.cineplex.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azad.cineplex.dto.HallDTO;
import com.azad.cineplex.entities.Hall;
import com.azad.cineplex.exceptions.ResourceCreationFailedException;
import com.azad.cineplex.repos.HallRepository;
import com.azad.cineplex.services.HallService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private HallRepository hallRepository;

	@Autowired
	public HallServiceImpl(HallRepository hallRepository) {
		this.hallRepository = hallRepository;
	}

	@Override
	public HallDTO create(HallDTO hallDTO) {
		
		Hall hall = hallRepository.save(modelMapper.map(hallDTO, Hall.class));
		
		if (hall == null) {
			throw new ResourceCreationFailedException("Failed to create resource of the entity 'Hall'");
		}
		
		return modelMapper.map(hall, HallDTO.class);
	}

}
