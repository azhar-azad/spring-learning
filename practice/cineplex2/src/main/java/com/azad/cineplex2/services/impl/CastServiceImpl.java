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
import com.azad.cineplex2.entities.Cast;
import com.azad.cineplex2.exceptions.ResourceCreationFailedException;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.CastRepository;
import com.azad.cineplex2.services.CastService;
import com.azad.cineplex2.utils.AppUtils;

@Service
public class CastServiceImpl implements CastService {
	
	private ModelMapper modelMapper;
	private CastRepository castRepository;

	@Autowired
	public CastServiceImpl(ModelMapper modelMapper, CastRepository castRepository) {
		this.modelMapper = modelMapper;
		this.castRepository = castRepository;
	}

	@Override
	public MoviePersonnelDto create(MoviePersonnelDto castDto) {

		if (castDto.getBirthDate() == null) {
			castDto.setBirthDate(new Date());
		}
			
		if (castDto.getDeathDate() != null) {
			castDto.setIsDead(true);
			castDto.setAge(AppUtils.getAge(castDto.getBirthDate(), castDto.getDeathDate()));
		} else {
			castDto.setAge(AppUtils.getAge(castDto.getBirthDate()));
		}

		Cast cast = castRepository.save(modelMapper.map(castDto, Cast.class));
		
		if (cast == null) {
			throw new ResourceCreationFailedException("Failed to create resource of the entity 'Cast'");
		}
		
		return modelMapper.map(cast, MoviePersonnelDto.class);
	}

	@Override
	public List<MoviePersonnelDto> getAll(int page, int limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		List<Cast> casts = castRepository.findAll(pageable).getContent();
		
		List<MoviePersonnelDto> castDtos = new ArrayList<>();
		casts.forEach(cast -> castDtos.add(modelMapper.map(cast, MoviePersonnelDto.class)));
		
		return castDtos;
	}

	@Override
	public List<MoviePersonnelDto> getAll(int page, int limit, String sort, String order) {
		
		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<Cast> casts = castRepository.findAll(pageable).getContent();
		
		List<MoviePersonnelDto> castDtos = new ArrayList<>();
		casts.forEach(cast -> castDtos.add(modelMapper.map(cast, MoviePersonnelDto.class)));
		
		return castDtos;
	}

	@Override
	public MoviePersonnelDto getById(Long id) {
		Cast cast = castRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cast", id));
		
		if (cast == null) {
			return null;
		}
		
		return modelMapper.map(cast, MoviePersonnelDto.class);
	}

	@Override
	public MoviePersonnelDto updateById(Long id, MoviePersonnelDto updatedCastDto) {
		Cast cast = castRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cast", id));
		
		if (updatedCastDto.getFullName() != null) {
			cast.setFullName(updatedCastDto.getFullName());
		}
		if (updatedCastDto.getStageName() != null) {
			cast.setStageName(updatedCastDto.getStageName());
		}
		if (updatedCastDto.getGender() != null) {
			cast.setGender(updatedCastDto.getGender());
		}
		if (updatedCastDto.getBirthDate() != null) {
			cast.setBirthDate(updatedCastDto.getBirthDate());
		}
		if (updatedCastDto.getIsDead() != null) {
			cast.setIsDead(updatedCastDto.getIsDead());
		}
		if (updatedCastDto.getDeathDate() != null) {
			cast.setIsDead(true);
			cast.setDeathDate(updatedCastDto.getDeathDate());
			cast.setAge(AppUtils.getAge(cast.getBirthDate(), cast.getDeathDate()));
		} else {
			cast.setAge(AppUtils.getAge(cast.getBirthDate()));
		}
		
		Cast updatedCast = castRepository.save(cast);
		
		return modelMapper.map(updatedCast, MoviePersonnelDto.class);
	}

	@Override
	public void deleteById(Long id) {
		Cast cast = castRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cast", id));

		castRepository.delete(cast);
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
	public MoviePersonnelDto getByFullName(String castFullName) {
		
		Cast cast = castRepository.findByFullName(castFullName);
		
		if (cast == null) {
			throw new ResourceNotFoundException("Cast", "fullName");
		}
		
		return modelMapper.map(cast, MoviePersonnelDto.class);
	}

}
