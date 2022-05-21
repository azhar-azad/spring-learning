package com.azad.cineplex2.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.cineplex2.dto.HallDto;
import com.azad.cineplex2.entities.Hall;
import com.azad.cineplex2.exceptions.ResourceCreationFailedException;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.HallRepository;
import com.azad.cineplex2.services.HallService;
import com.azad.cineplex2.utils.AppUtils;

@Service
public class HallServiceImpl implements HallService {
	
	private ModelMapper modelMapper;
	private HallRepository hallRepository;
	
	@Autowired
	public HallServiceImpl(ModelMapper modelMapper, HallRepository hallRepository) {
		super();
		this.modelMapper = modelMapper;
		this.hallRepository = hallRepository;
	}

	@Override
	public HallDto create(HallDto hallDto) {
		
		Hall hall = hallRepository.save(modelMapper.map(hallDto, Hall.class));
		
		if (hall == null) {
			throw new ResourceCreationFailedException("Failed to create the resource; entity: Hall");
		}
		
		return modelMapper.map(hall, HallDto.class);
	}

	@Override
	public List<HallDto> getAll(int page, int limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		List<Hall> halls = hallRepository.findAll(pageable).getContent();
		
		List<HallDto> hallDtos = new ArrayList<>();
		halls.forEach(hall -> hallDtos.add(modelMapper.map(hall, HallDto.class)));
		
		return hallDtos;
	}

	@Override
	public List<HallDto> getAll(int page, int limit, String sort, String order) {
		
		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<Hall> halls = hallRepository.findAll(pageable).getContent();
		
		List<HallDto> hallDtos = new ArrayList<>();
		halls.forEach(hall -> hallDtos.add(modelMapper.map(hall, HallDto.class)));
		
		return hallDtos;
	}

	@Override
	public HallDto getById(Long id) {

		Hall hall = hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hall", id));
		
		if (hall == null) {
			return null;
		}
		
		return modelMapper.map(hall, HallDto.class);
	}

	@Override
	public HallDto updateById(Long id, HallDto updatedHallDto) {

		Hall hall = hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hall", id));
		
		hall.setName(updatedHallDto.getName());
		hall.setSerialNo(updatedHallDto.getSerialNo());
		hall.setCapacity(updatedHallDto.getCapacity());
		hall.setFloor(updatedHallDto.getFloor());
		
		Hall updatedHall = hallRepository.save(hall);
		
		return modelMapper.map(updatedHall, HallDto.class);
	}

	@Override
	public void deleteById(Long id) {

		Hall hall = hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hall", id));
		
		hallRepository.delete(hall);
	}

	@Override
	public HallDto update(HallDto object, HallDto updatedHallDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(HallDto hallDto) {
		// TODO Auto-generated method stub
	}

	@Override
	public HallDto getByName(String name) {
		
		Hall hall = hallRepository.findByName(name);
		
		if (hall == null) {
			throw new ResourceNotFoundException("Hall", "name");
		}
		
		return modelMapper.map(hall, HallDto.class);
	}

}
