package com.azad.cineplex.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.cineplex.dto.HallDTO;
import com.azad.cineplex.entities.Hall;
import com.azad.cineplex.exceptions.InvalidSortItemsException;
import com.azad.cineplex.exceptions.ResourceCreationFailedException;
import com.azad.cineplex.repos.HallRepository;
import com.azad.cineplex.services.HallService;
import com.azad.cineplex.utility.Utils;

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

	@Override
	public List<HallDTO> getAll(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		
		List<Hall> halls = hallRepository.findAll(pageable).getContent();
		
		List<HallDTO> hallDTOs = new ArrayList<>();
		halls.forEach(hall -> hallDTOs.add(modelMapper.map(hall, HallDTO.class)));
		
		return hallDTOs;
	}

	@Override
	public List<HallDTO> getAll(int page, int limit, String sort, String order) {
		
		List<String> sortItems = Arrays.asList(sort.split(","));
		
		boolean sortItemsAreValid = Utils.sortItemsAreValid("Hall", sortItems);
		
		if (!sortItemsAreValid) {
			throw new InvalidSortItemsException("Sort items are invalid for the resource: Hall");
		}
		
		Sort sortBy = Sort.by(sortItems.get(0));
		for (int i = 1; i < sortItems.size(); i++) {
			sortBy = sortBy.and(Sort.by(sortItems.get(i)));
		}
		
		if (order.equalsIgnoreCase("desc")) {
			sortBy = sortBy.descending();
		}
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<Hall> halls = hallRepository.findAll(pageable).getContent();
		
		List<HallDTO> hallDTOs = new ArrayList<>();
		halls.forEach(hall -> hallDTOs.add(modelMapper.map(hall, HallDTO.class)));
		
		return hallDTOs;
	}

	@Override
	public HallDTO getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HallDTO updateById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
