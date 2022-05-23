package com.azad.cineplex2.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.cineplex2.dto.MoviePersonnelDto;
import com.azad.cineplex2.entities.Director;
import com.azad.cineplex2.exceptions.ResourceCreationFailedException;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.DirectorRepository;
import com.azad.cineplex2.services.DirectorService;
import com.azad.cineplex2.utils.AppUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DirectorServiceImpl implements DirectorService {
	
	private ModelMapper modelMapper;
	private DirectorRepository directorRepository;
	
	@Autowired
	public DirectorServiceImpl(ModelMapper modelMapper, DirectorRepository directorRepository) {
		this.modelMapper = modelMapper;
		this.directorRepository = directorRepository;
	}

	@Override
	public MoviePersonnelDto create(MoviePersonnelDto directorDto) {
		
		if (directorDto.getBirthDate() == null) {
			directorDto.setBirthDate(new Date());
		}
		
		if (directorDto.getDeathDate() != null) {
			directorDto.setIsDead(true);
			directorDto.setAge(AppUtils.getAge(directorDto.getBirthDate(), directorDto.getDeathDate()));
		} else {
			directorDto.setAge(AppUtils.getAgeFromBirthDate(directorDto.getBirthDate()));
		}

		Director director = directorRepository.save(modelMapper.map(directorDto, Director.class));
		
		if (director == null) {
			throw new ResourceCreationFailedException("Failed to create resource of the entity 'Director'");
		}
		
		return modelMapper.map(director, MoviePersonnelDto.class);
	}

	@Override
	public List<MoviePersonnelDto> getAll(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		
		List<Director> directors = directorRepository.findAll(pageable).getContent();
		
		List<MoviePersonnelDto> directorDtos = new ArrayList<>();
		directors.forEach(director -> directorDtos.add(modelMapper.map(director, MoviePersonnelDto.class)));
		
		return directorDtos;
	}

	@Override
	public List<MoviePersonnelDto> getAll(int page, int limit, String sort, String order) {

		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<Director> directors = directorRepository.findAll(pageable).getContent();
		
		List<MoviePersonnelDto> directorDtos = new ArrayList<>();
		directors.forEach(director -> directorDtos.add(modelMapper.map(director, MoviePersonnelDto.class)));
		
		return directorDtos;
	}

	@Override
	public MoviePersonnelDto getById(Long id) {

		Director director = directorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Director", id));
		
		if (director == null) {
			return null;
		}
		
		return modelMapper.map(director, MoviePersonnelDto.class);
	}

	@Override
	public MoviePersonnelDto updateById(Long id, MoviePersonnelDto updatedDirectorDto) {

		Director director = directorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Director", id));
		
		if (updatedDirectorDto.getFullName() != null) {
			director.setFullName(updatedDirectorDto.getFullName());
		}
		if (updatedDirectorDto.getGender() != null) {
			director.setGender(updatedDirectorDto.getGender());
		}
		if (updatedDirectorDto.getBirthDate() != null) {
			director.setBirthDate(updatedDirectorDto.getBirthDate());
		}
		if (updatedDirectorDto.getIsDead() != null) {
			director.setIsDead(updatedDirectorDto.getIsDead());
		}
		if (updatedDirectorDto.getDeathDate() != null && updatedDirectorDto.getIsDead()) {
			director.setDeathDate(updatedDirectorDto.getDeathDate());
			director.setAge(AppUtils.getAge(director.getBirthDate(), director.getDeathDate()));
		} else {
			director.setAge(AppUtils.getAgeFromBirthDate(director.getBirthDate()));
		}
		
		Director updatedDirector = directorRepository.save(director);
		
		return modelMapper.map(updatedDirector, MoviePersonnelDto.class);
	}

	@Override
	public void deleteById(Long id) {
		
		Director director = directorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Director", id));
		
		directorRepository.delete(director);
	}

	@Override
	public MoviePersonnelDto update(MoviePersonnelDto object, MoviePersonnelDto updatedObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(MoviePersonnelDto object) {
		// TODO Auto-generated method stub

	}

	@Override
	public MoviePersonnelDto getByFullName(String directorFullName) {

		Director director = directorRepository.findByFullName(directorFullName);
		
		if (director == null) {
			throw new ResourceNotFoundException("Director", "fullName");
		}
		
		return modelMapper.map(director, MoviePersonnelDto.class);
	}

}
