package com.azad.cineplex2.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azad.cineplex2.dto.MoviePersonnelDto;
import com.azad.cineplex2.entities.Director;
import com.azad.cineplex2.exceptions.ResourceCreationFailedException;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.DirectorRepository;
import com.azad.cineplex2.services.DirectorService;

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

		Director director = directorRepository.save(modelMapper.map(directorDto, Director.class));
		
		if (director == null) {
			throw new ResourceCreationFailedException("Failed to create resource of the entity 'Director'");
		}
		
		return modelMapper.map(director, MoviePersonnelDto.class);
	}

	@Override
	public List<MoviePersonnelDto> getAll(int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MoviePersonnelDto> getAll(int page, int limit, String sort, String order) {
		// TODO Auto-generated method stub
		return null;
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
	public MoviePersonnelDto updateById(Long id, MoviePersonnelDto updatedObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoviePersonnelDto update(MoviePersonnelDto object, MoviePersonnelDto updatedObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

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
