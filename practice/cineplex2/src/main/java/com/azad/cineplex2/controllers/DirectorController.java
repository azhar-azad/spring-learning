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

import com.azad.cineplex2.dto.MoviePersonnelDto;
import com.azad.cineplex2.dto.requests.MoviePersonnelRequest;
import com.azad.cineplex2.dto.responses.MoviePersonnelResponse;
import com.azad.cineplex2.dto.responses.MoviePersonnelResponseBasic;
import com.azad.cineplex2.exceptions.InvalidPathVariableException;
import com.azad.cineplex2.exceptions.RequestBodyEmptyException;
import com.azad.cineplex2.services.DirectorService;
import com.azad.cineplex2.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/directors")
public class DirectorController {

	private ModelMapper modelMapper;
	private DirectorService directorService;
	
	@Autowired
	public DirectorController(ModelMapper modelMapper, DirectorService directorService) {
		this.modelMapper = modelMapper;
		this.directorService = directorService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> directorRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/directors/test", "directorRouteTest");
		
		return ResponseEntity.ok().body("{\"status\":\"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<MoviePersonnelResponse> createDirector(@Valid @RequestBody MoviePersonnelRequest directorRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/directors", "createDirector");
		
		if (directorRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Director");
		}
		
		MoviePersonnelDto directorDto = directorService.create(modelMapper.map(directorRequest, MoviePersonnelDto.class));
		
		MoviePersonnelResponse directorResponse = modelMapper.map(directorDto, MoviePersonnelResponse.class);
		
		return new ResponseEntity<MoviePersonnelResponse>(directorResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<MoviePersonnelResponseBasic>> getAllDirectors(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/directors", "getAllDirectors");
		
		if (page > 0) {
			page--;
		}
		
		List<MoviePersonnelDto> directorDtos = null;
		
		if (sort.isEmpty()) {
			directorDtos = directorService.getAll(page, limit);
		} else {
			directorDtos = directorService.getAll(page, limit, sort, order);
		}
		
		if (directorDtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<MoviePersonnelResponseBasic> directorResponses = new ArrayList<>();
		directorDtos.forEach(directorDto -> directorResponses.add(modelMapper.map(directorDto, MoviePersonnelResponseBasic.class)));
		
		return new ResponseEntity<List<MoviePersonnelResponseBasic>>(directorResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<MoviePersonnelResponse> getDirectorById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/directors/{id}", "getDirectorById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Director, path: /api/v1/directors/{id}");
		}
		
		MoviePersonnelDto directorDto = directorService.getById(id);
		
		if (directorDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MoviePersonnelResponse>(modelMapper.map(directorDto, MoviePersonnelResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<MoviePersonnelResponse> updateDirectorById(@Valid @PathVariable Long id, @RequestBody MoviePersonnelRequest directorRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/directors/{id}", "updateDirectorById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Director, path: /api/v1/directors/{id}");
		}
		
		if (directorRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Director");
		}
		
		MoviePersonnelDto directorDto = directorService.updateById(id, modelMapper.map(directorRequest, MoviePersonnelDto.class));
		
		return new ResponseEntity<MoviePersonnelResponse>(modelMapper.map(directorDto, MoviePersonnelResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteDirectorById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/directors/{id}", "deleteDirectorById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Director, path: /api/v1/directors/{id}");
		}
		
		directorService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
