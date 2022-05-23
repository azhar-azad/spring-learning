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
import com.azad.cineplex2.dto.MoviePersonnelDto;
import com.azad.cineplex2.entities.Cast;
import com.azad.cineplex2.entities.Director;
import com.azad.cineplex2.entities.Genre;
import com.azad.cineplex2.entities.Movie;
import com.azad.cineplex2.exceptions.ResourceNotFoundException;
import com.azad.cineplex2.repositories.MovieRepository;
import com.azad.cineplex2.services.CastService;
import com.azad.cineplex2.services.DirectorService;
import com.azad.cineplex2.services.GenreService;
import com.azad.cineplex2.services.MovieService;
import com.azad.cineplex2.utils.AppUtils;

@Service
public class MovieServiceImpl implements MovieService {
	
	private ModelMapper modelMapper;
	private MovieRepository movieRepository;
	private GenreService genreService;
	private DirectorService directorService;
	private CastService castService;
	
	@Autowired
	public MovieServiceImpl(ModelMapper modelMapper, MovieRepository movieRepository,
			GenreService genreService, DirectorService directorService, CastService castService) {
		this.modelMapper = modelMapper;
		this.movieRepository = movieRepository;
		this.genreService = genreService;
		this.directorService = directorService;
		this.castService = castService;
	}

	@Override
	public MovieDto create(MovieDto movieDto) {
		
		// Convert the data from dto to entity object
		Movie movie = modelMapper.map(movieDto, Movie.class);

		// Get the genres by genreId or genreName; if not found, create
		List<Genre> genres = getGenres(movieDto);
		movie.setGenres(genres);
		
		// Get the directors by directorId or directorFullName; if not found, create
		List<Director> directors = getDirectors(movieDto);
		movie.setDirectors(directors);
		
		// Get the casts by castid or castFullName; if not found, create
		List<Cast> casts = getCasts(movieDto);
		movie.setCasts(casts);
		
		// save the entity and get the dto
		MovieDto savedMovieDto = modelMapper.map(movieRepository.save(movie), MovieDto.class);
		
		// Set genre names on dto to populate the response object
		List<String> genreNames = new ArrayList<>();
		for (Genre genre: movie.getGenres()) {
			genreNames.add(genre.getName());
		}
		savedMovieDto.setGenreNames(genreNames);
		
		// Set director names on dto to populate the response object
		List<String> directorFullNames = new ArrayList<>();
		for (Director director: movie.getDirectors()) {
			directorFullNames.add(director.getFullName());
		}
		savedMovieDto.setDirectorFullNames(directorFullNames);
		
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
		
		List<Genre> genres = getGenres(updatedMovieDto);
		if (genres != null && genres.size() != 0) {
			movie.setGenres(genres);			
		}
		
		List<Director> directors = getDirectors(updatedMovieDto);
		if (directors != null && directors.size() != 0) {
			movie.setDirectors(directors);
		}
		
		List<Cast> casts = getCasts(updatedMovieDto);
		if (casts != null && casts.size() != 0) {
			movie.setCasts(casts);
		}
		
		if (updatedMovieDto.getTitle() != null) {
			movie.setTitle(updatedMovieDto.getTitle());
		}
		if (updatedMovieDto.getSummary() != null) {
			movie.setSummary(updatedMovieDto.getSummary());
		}
		if (updatedMovieDto.getReleaseYear() >= 1700) {
			movie.setReleaseYear(updatedMovieDto.getReleaseYear());
		}
		
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
	
	private List<Genre> getGenres(MovieDto movieDto) {
		
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
					genreDto = genreService.getByName(genreName.trim());
				} catch (ResourceNotFoundException ex) {
					genreDto = null;
				}
				if (genreDto == null) {
					// Create a new Genre
					GenreDto newGenreDto = new GenreDto();
					newGenreDto.setName(genreName.trim());
					genreDto = genreService.create(newGenreDto);
				}
				genreDtos.add(genreDto);
			}
		} else {
			genreDtos = null;
		}
		
		List<Genre> genres = new ArrayList<>();
		if (genreDtos != null) {
			genreDtos.forEach(genreDto -> genres.add(modelMapper.map(genreDto, Genre.class)));
		}

		return genres;
	}
	
	private List<Director> getDirectors(MovieDto movieDto) {

		List<MoviePersonnelDto> directorDtos = new ArrayList<>();
		
		if (movieDto.getDirectorIds() != null && !movieDto.getDirectorIds().isEmpty()) {
			for (Long directorId: movieDto.getDirectorIds()) {
				// as directorId is passed, that director had to be present on the database
				directorDtos.add(directorService.getById(directorId)); 
			}
		} else if (movieDto.getDirectorFullNames() != null && !movieDto.getDirectorFullNames().isEmpty()) {
			for (String directorFullName: movieDto.getDirectorFullNames()) {
				MoviePersonnelDto directorDto = null;
				try {
					directorDto = directorService.getByFullName(directorFullName.trim());
				} catch (ResourceNotFoundException ex) {
					directorDto = null;
				}
				
				if (directorDto == null) {
					// Create a new Director
					MoviePersonnelDto newDirectorDto = new MoviePersonnelDto();
					newDirectorDto.setFullName(directorFullName.trim());
					directorDto = directorService.create(newDirectorDto); 
				}
				directorDtos.add(directorDto);
			} 
		} else {
			directorDtos = null;
		}
		
		List<Director> directors = new ArrayList<>();
		if (directorDtos != null) {
			directorDtos.forEach(directorDto -> directors.add(modelMapper.map(directorDto, Director.class)));	
		}
		
		return directors;
	}
	
	private List<Cast> getCasts(MovieDto movieDto) {

		List<MoviePersonnelDto> castDtos = new ArrayList<>();
		
		if (movieDto.getCastIds() != null && !movieDto.getCastIds().isEmpty()) {
			for (Long castId: movieDto.getCastIds()) {
				// as castId is passed, that cast had to be present on the database
				castDtos.add(castService.getById(castId)); 
			}
		} else if (movieDto.getCastFullNames() != null && !movieDto.getCastFullNames().isEmpty()) {
			for (String castFullName: movieDto.getCastFullNames()) {
				MoviePersonnelDto castDto = null;
				try {
					castDto = castService.getByFullName(castFullName.trim());
				} catch (ResourceNotFoundException ex) {
					castDto = null;
				}
				
				if (castDto == null) {
					// Create a new Director
					MoviePersonnelDto newCastDto = new MoviePersonnelDto();
					newCastDto.setFullName(castFullName.trim());
					castDto = castService.create(newCastDto); 
				}
				castDtos.add(castDto);
			} 
		} else {
			castDtos = null;
		}
		
		List<Cast> casts = new ArrayList<>();
		if (castDtos != null) {
			castDtos.forEach(castDto -> casts.add(modelMapper.map(castDto, Cast.class)));	
		}
		
		return casts;
	}

}
