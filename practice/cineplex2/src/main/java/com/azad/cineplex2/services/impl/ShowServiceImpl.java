package com.azad.cineplex2.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.cineplex2.dto.HallDto;
import com.azad.cineplex2.dto.ShowDto;
import com.azad.cineplex2.entities.Hall;
import com.azad.cineplex2.entities.Show;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.ShowRepository;
import com.azad.cineplex2.services.HallService;
import com.azad.cineplex2.services.ShowService;
import com.azad.cineplex2.utils.AppUtils;

@Service
public class ShowServiceImpl implements ShowService {
	
	private ModelMapper modelMapper;
	private ShowRepository showRepository;
	private HallService hallService;
	
	@Autowired
	public ShowServiceImpl(ModelMapper modelMapper, ShowRepository showRepository, HallService hallService) {
		this.modelMapper = modelMapper;
		this.showRepository = showRepository;
		this.hallService = hallService;
	}

	@Override
	public ShowDto create(ShowDto showDto) {
		
		HallDto hallDto;
		if (showDto.getHallId() != null) {
			hallDto = hallService.getById(showDto.getHallId());
		} else if (showDto.getHallName() != null && !showDto.getHallName().equals("")) {
			hallDto = hallService.getByName(showDto.getHallName());
		} else {
			hallDto = null;
		}
		
		Show show = modelMapper.map(showDto, Show.class);
		show.setHall(modelMapper.map(hallDto, Hall.class));
		
		ShowDto savedShowDto = modelMapper.map(showRepository.save(show), ShowDto.class);
		savedShowDto.setHallName(show.getHall().getName());
		
		return savedShowDto;
	}

	@Override
	public List<ShowDto> getAll(int page, int limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		List<Show> shows = showRepository.findAll(pageable).getContent();
		
		List<ShowDto> showDtos = new ArrayList<>();
		shows.forEach(show -> showDtos.add(modelMapper.map(show, ShowDto.class)));
		
		return showDtos;
	}

	@Override
	public List<ShowDto> getAll(int page, int limit, String sort, String order) {
		
		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<Show> shows = showRepository.findAll(pageable).getContent();
		
		List<ShowDto> showDtos = new ArrayList<>();
		shows.forEach(show -> showDtos.add(modelMapper.map(show, ShowDto.class)));
		
		return showDtos;
	}

	@Override
	public ShowDto getById(Long id) {

		Show show = showRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Show", id));
		
		if (show == null) {
			return null;
		}
		
		return modelMapper.map(show, ShowDto.class);
	}

	@Override
	public ShowDto updateById(Long id, ShowDto updatedShowDto) {
		
		HallDto hallDto;
		if (updatedShowDto.getHallId() != null) {
			hallDto = hallService.getById(updatedShowDto.getHallId());
		} else if (updatedShowDto.getHallName() != null && !updatedShowDto.getHallName().equals("")) {
			hallDto = hallService.getByName(updatedShowDto.getHallName());
		} else {
			hallDto = null;
		}

		Show show = showRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Show", id));
		
		if (updatedShowDto.getName() != null) {
			show.setName(updatedShowDto.getName());
		}
		if (updatedShowDto.getShowTime() != null) {
			show.setShowTime(updatedShowDto.getShowTime());			
		}
		if (hallDto != null) {
			show.setHall(modelMapper.map(hallDto, Hall.class));
		}
		
		Show updatedShow = showRepository.save(show);
		
		return modelMapper.map(updatedShow, ShowDto.class);
	}

	@Override
	public void deleteById(Long id) {

		Show show = showRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Show", id));
		
		showRepository.delete(show);
	}

	@Override
	public ShowDto update(ShowDto object, ShowDto updatedObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ShowDto object) {
		// TODO Auto-generated method stub

	}

}
