package com.azad.cineplex2.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azad.cineplex2.dto.MovieDto;
import com.azad.cineplex2.dto.requests.MovieRequest;
import com.azad.cineplex2.dto.responses.MovieResponse;
import com.azad.cineplex2.exceptions.InvalidPathVariableException;
import com.azad.cineplex2.exceptions.RequestBodyEmptyException;
import com.azad.cineplex2.services.MovieService;
import com.azad.cineplex2.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/movies")
public class MovieController {

	private ModelMapper modelMapper;
	private MovieService movieService;
	
	@Autowired
	public MovieController(ModelMapper modelMapper, MovieService movieService) {
		this.modelMapper = modelMapper;
		this.movieService = movieService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> movieRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/movies/test", "movieRouteTest");
		
		return ResponseEntity.ok().body("{\"status\":\"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/movies", "createMovie");
		
		if (movieRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Movie");
		}
		
		MovieDto movieDto = movieService.create(modelMapper.map(movieRequest, MovieDto.class));
			
		return new ResponseEntity<MovieResponse>(modelMapper.map(movieDto, MovieResponse.class), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<MovieResponse>> getAllMovies(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/movies", "getAllMovies");
		
		if (page > 0) {
			page--;
		}
		
		List<MovieDto> movieDtos = null;
		
		if (sort.isEmpty()) {
			movieDtos = movieService.getAll(page, limit);
		} else {
			movieDtos = movieService.getAll(page, limit, sort, order);
		}
		
		if (movieDtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<MovieResponse> movieResponses = new ArrayList<>();
		movieDtos.forEach(movieDto -> movieResponses.add(modelMapper.map(movieDto, MovieResponse.class)));
		
		return new ResponseEntity<List<MovieResponse>>(movieResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<MovieResponse> getMovieById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/movies/{id}", "getMovieById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Movie, path: /api/v1/movies/{id}");
		}
		
		MovieDto movieDto = movieService.getById(id);
		
		if (movieDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MovieResponse>(modelMapper.map(movieDto, MovieResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<MovieResponse> updateMovieById(@Valid @PathVariable Long id, @Valid @RequestBody MovieRequest movieRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/movies/{id}", "updateMovieById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Movie, path: /api/v1/movies/{id}");
		}
		
		if (movieRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Movie");
		}
		
		MovieDto movieDto = movieService.updateById(id, modelMapper.map(movieRequest, MovieDto.class));
		
		return new ResponseEntity<MovieResponse>(modelMapper.map(movieDto, MovieResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> deleteMovieById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/movies/{id}", "deleteMovieById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Movie, path: /api/v1/movies/{id}");
		}
		
		movieService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
