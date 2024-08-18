package com.azad.moviepedia.services.impl;

import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.dtos.MovieDto;
import com.azad.moviepedia.repositories.MovieRepository;
import com.azad.moviepedia.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private ModelMapper modelMapper;

    private final MovieRepository repository;

    @Autowired
    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovieDto create(MovieDto dto) throws RuntimeException {
        return null;
    }

    @Override
    public List<MovieDto> getAll(PagingAndSorting ps) throws RuntimeException {
        return List.of();
    }

    @Override
    public MovieDto getById(Long id) throws RuntimeException {
        return null;
    }

    @Override
    public MovieDto updateById(Long id, MovieDto updatedDto) throws RuntimeException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws RuntimeException {

    }
}
