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

import com.azad.cineplex2.dto.GenreDto;
import com.azad.cineplex2.dto.requests.GenreRequest;
import com.azad.cineplex2.dto.responses.GenreResponse;
import com.azad.cineplex2.dto.responses.GenreResponseBasic;
import com.azad.cineplex2.exceptions.InvalidPathVariableException;
import com.azad.cineplex2.exceptions.RequestBodyEmptyException;
import com.azad.cineplex2.services.GenreService;
import com.azad.cineplex2.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/genres")
public class GenreController {

	private ModelMapper modelMapper;
	private GenreService genreService;
	
	@Autowired
	public GenreController(ModelMapper modelMapper, GenreService genreService) {
		this.modelMapper = modelMapper;
		this.genreService = genreService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> genreRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/genres/test", "genreRouteTest");
		
		return ResponseEntity.ok().body("{\"status\":\"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<GenreResponse> createGenre(@Valid @RequestBody GenreRequest genreRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/genres", "createGenre");
		
		if (genreRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Genre");
		}
		
		GenreDto genreDto = genreService.create(modelMapper.map(genreRequest, GenreDto.class));
		
		GenreResponse genreResponse = modelMapper.map(genreDto, GenreResponse.class);
		
		return new ResponseEntity<GenreResponse>(genreResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<GenreResponseBasic>> getAllGenres(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/genres", "getAllGenres");
		
		if (page > 0) {
			page--;
		}
		
		List<GenreDto> genreDtos = null;
		
		if (sort.isEmpty()) {
			genreDtos = genreService.getAll(page, limit);
		} else {
			genreDtos = genreService.getAll(page, limit, sort, order);
		}
		
		if (genreDtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<GenreResponseBasic> genreResponses = new ArrayList<>();
		genreDtos.forEach(genreDto -> genreResponses.add(modelMapper.map(genreDto, GenreResponseBasic.class)));
		
		return new ResponseEntity<List<GenreResponseBasic>>(genreResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<GenreResponse> getGenreById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/genres/{id}", "getGenreById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Genre, path: /api/v1/genres/{id}");
		}
		
		GenreDto genreDto = genreService.getById(id);
		
		if (genreDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<GenreResponse>(modelMapper.map(genreDto, GenreResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<GenreResponse> updateGenreById(@Valid @PathVariable Long id, @RequestBody GenreRequest genreRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/genres/{id}", "updateGenreById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Genre, path: /api/v1/genres/{id}");
		}
		
		if (genreRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Genre");
		}
		
		GenreDto genreDto = genreService.updateById(id, modelMapper.map(genreRequest, GenreDto.class));
		
		return new ResponseEntity<GenreResponse>(modelMapper.map(genreDto, GenreResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteGenreById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/genres/{id}", "deleteGenreById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Genre, path: /api/v1/genres/{id}");
		}
		
		genreService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
