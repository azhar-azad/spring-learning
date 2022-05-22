package com.azad.cineplex2.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.cineplex2.dto.GenreDto;
import com.azad.cineplex2.entities.Genre;
import com.azad.cineplex2.exceptions.ResourceCreationFailedException;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.GenreRepository;
import com.azad.cineplex2.services.GenreService;
import com.azad.cineplex2.utils.AppUtils;

@Service
public class GenreServiceImpl implements GenreService {
	
	private ModelMapper modelMapper;
	private GenreRepository genreRepository;
	
	@Autowired
	public GenreServiceImpl(ModelMapper modelMapper, GenreRepository genreRepository) {
		this.modelMapper = modelMapper;
		this.genreRepository = genreRepository;
	}

	@Override
	public GenreDto create(GenreDto genreDto) {

		Genre genre = genreRepository.save(modelMapper.map(genreDto, Genre.class));
		
		if (genre == null) {
			throw new ResourceCreationFailedException("Failed to create resource of the entity 'Genre'");
		}
		
		return modelMapper.map(genre, GenreDto.class);
	}

	@Override
	public List<GenreDto> getAll(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		
		List<Genre> genres = genreRepository.findAll(pageable).getContent();
		
		List<GenreDto> genreDtos = new ArrayList<>();
		genres.forEach(genre -> genreDtos.add(modelMapper.map(genre, GenreDto.class)));
		
		return genreDtos;
	}

	@Override
	public List<GenreDto> getAll(int page, int limit, String sort, String order) {

		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<Genre> genres = genreRepository.findAll(pageable).getContent();
		
		List<GenreDto> genreDtos = new ArrayList<>();
		genres.forEach(genre -> genreDtos.add(modelMapper.map(genre, GenreDto.class)));
		
		return genreDtos;
	}

	@Override
	public GenreDto getById(Long id) {

		Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre", id));
		
		if (genre == null) {
			return null;
		}
		
		return modelMapper.map(genre, GenreDto.class);
	}

	@Override
	public GenreDto updateById(Long id, GenreDto updatedGenreDto) {

		Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre", id));
		
		genre.setName(updatedGenreDto.getName());
		
		Genre updatedGenre = genreRepository.save(genre);
		
		return modelMapper.map(updatedGenre, GenreDto.class);
	}

	@Override
	public void deleteById(Long id) {
		
		Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre", id));
		
		genreRepository.delete(genre);
	}

	@Override
	public GenreDto update(GenreDto object, GenreDto updatedObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(GenreDto object) {
		// TODO Auto-generated method stub

	}

	@Override
	public GenreDto getByName(String name) {
		
		Genre genre = genreRepository.findByName(name);
		
		if (genre == null) {
			throw new ResourceNotFoundException("Genre", "name");
		}
		
		return modelMapper.map(genre, GenreDto.class);
	}

}
