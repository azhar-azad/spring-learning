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

import com.azad.cineplex2.dto.GenreDto;
import com.azad.cineplex2.dto.MovieDto;
import com.azad.cineplex2.entities.Genre;
import com.azad.cineplex2.entities.Movie;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.MovieRepository;
import com.azad.cineplex2.services.GenreService;
import com.azad.cineplex2.services.MovieService;
import com.azad.cineplex2.utils.AppUtils;

@Service
public class MovieServiceImpl implements MovieService {
	
	private ModelMapper modelMapper;
	private MovieRepository movieRepository;
	private GenreService genreService;
	
	@Autowired
	public MovieServiceImpl(ModelMapper modelMapper, MovieRepository movieRepository, GenreService genreService) {
		this.modelMapper = modelMapper;
		this.movieRepository = movieRepository;
		this.genreService = genreService;
	}

	@Override
	public MovieDto create(MovieDto movieDto) {
		
		Movie movie = modelMapper.map(movieDto, Movie.class);

		List<GenreDto> genreDtos = new ArrayList<>();
		if (movieDto.getGenreIds() != null && !movieDto.getGenreIds().isEmpty()) {
			for (Long genreId: movieDto.getGenreIds()) {
				// as genreId is passed, that genre had to be present on the database
				genreDtos.add(genreService.getById(genreId));
			}
		} else if (movieDto.getGenreNames() != null && !movieDto.getGenreNames().isEmpty()) {
			for (String genreName: movieDto.getGenreNames()) {
				GenreDto genreDto = null;
				try {
					genreDto = genreService.getByName(genreName);
				} catch (ResourceNotFoundException ex) {
					genreDto = null;
				}
				if (genreDto == null) {
					// Create a new Genre
					GenreDto newGenreDto = new GenreDto();
					newGenreDto.setName(genreName);
					newGenreDto.setMovies(Arrays.asList(movie));
					newGenreDto.setMovieTitles(Arrays.asList(movie.getTitle()));
					genreDto = genreService.create(newGenreDto);
				}
				genreDtos.add(genreDto);
			}
		} else {
			genreDtos = null;
		}
		
		List<Genre> genres = new ArrayList<>();
		genreDtos.forEach(genreDto -> genres.add(modelMapper.map(genreDto, Genre.class)));
		movie.setGenres(genres);
		
		MovieDto savedMovieDto = modelMapper.map(movieRepository.save(movie), MovieDto.class);
		
		// Set genre names to populate the response object
		List<String> genreNames = new ArrayList<>();
		for (Genre genre: movie.getGenres()) {
			genreNames.add(genre.getName());
		}
		savedMovieDto.setGenreNames(genreNames);
		
		return savedMovieDto;
	}

	@Override
	public List<MovieDto> getAll(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		
		List<Movie> movies = movieRepository.findAll(pageable).getContent();
		
		List<MovieDto> movieDtos = new ArrayList<>();
		movies.forEach(movie -> movieDtos.add(modelMapper.map(movie, MovieDto.class)));
		
		return movieDtos;
	}

	@Override
	public List<MovieDto> getAll(int page, int limit, String sort, String order) {

		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<Movie> movies = movieRepository.findAll(pageable).getContent();
		
		List<MovieDto> movieDtos = new ArrayList<>();
		movies.forEach(movie -> movieDtos.add(modelMapper.map(movie, MovieDto.class)));
		
		return movieDtos;
	}

	@Override
	public MovieDto getById(Long id) {

		Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", id));
		
		if (movie == null) {
			return null;
		}
		
		return modelMapper.map(movie, MovieDto.class);
	}

	@Override
	public MovieDto updateById(Long id, MovieDto updatedMovieDto) {
		
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", id));

		List<GenreDto> genreDtos = new ArrayList<>();
		if (updatedMovieDto.getGenreIds() != null && !updatedMovieDto.getGenreIds().isEmpty()) {
			for (Long genreId: updatedMovieDto.getGenreIds()) {
				// as genreId is passed, that genre had to be present on the database
				genreDtos.add(genreService.getById(genreId));
			}
		} else if (updatedMovieDto.getGenreNames() != null && !updatedMovieDto.getGenreNames().isEmpty()) {
			for (String genreName: updatedMovieDto.getGenreNames()) {
				GenreDto genreDto = null;
				try {
					genreDto = genreService.getByName(genreName);
				} catch (ResourceNotFoundException ex) {
					genreDto = null;
				}
				if (genreDto == null) {
					// Create a new Genre
					GenreDto newGenreDto = new GenreDto();
					newGenreDto.setName(genreName);
					newGenreDto.setMovies(Arrays.asList(movie));
					newGenreDto.setMovieTitles(Arrays.asList(movie.getTitle()));
					genreDto = genreService.create(newGenreDto);
				}
				genreDtos.add(genreDto);
			}
		} else {
			genreDtos = null;
		}
		
		List<Genre> genres = new ArrayList<>();
		genreDtos.forEach(genreDto -> genres.add(modelMapper.map(genreDto, Genre.class)));
		movie.setGenres(genres);
		
		movie.setTitle(updatedMovieDto.getTitle());
		movie.setSummary(updatedMovieDto.getSummary());
		movie.setReleaseYear(updatedMovieDto.getReleaseYear());
		
		Movie updatedMovie = movieRepository.save(movie);
		
		return modelMapper.map(updatedMovie, MovieDto.class);
	}

	@Override
	public void deleteById(Long id) {

		Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", id));
		
		movieRepository.delete(movie);
	}

	@Override
	public MovieDto update(MovieDto object, MovieDto updatedObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(MovieDto object) {
		// TODO Auto-generated method stub

	}

}
