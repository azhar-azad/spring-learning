package com.azad.imdbapi.services;

import com.azad.imdbapi.dtos.MovieDto;
import com.azad.imdbapi.utils.PagingAndSorting;

import java.util.List;

public interface MovieService extends GenericApiService<MovieDto> {

    List<MovieDto> getAllByGenre(String genreName);

    List<MovieDto> getAllByYear(String year, PagingAndSorting ps);
}
