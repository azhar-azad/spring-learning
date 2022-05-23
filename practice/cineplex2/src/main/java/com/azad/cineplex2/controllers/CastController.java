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
import com.azad.cineplex2.services.CastService;
import com.azad.cineplex2.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/casts")
public class CastController {

	private ModelMapper modelMapper;
	private CastService castService;
	
	@Autowired
	public CastController(ModelMapper modelMapper, CastService castService) {
		this.modelMapper = modelMapper;
		this.castService = castService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> castRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/casts/test", "castRouteTest");
		
		return ResponseEntity.ok().body("{\"status\":\"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<MoviePersonnelResponse> createCast(@Valid @RequestBody MoviePersonnelRequest castRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/casts", "createCast");
		
		if (castRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Cast");
		}
		
		MoviePersonnelDto castDto = castService.create(modelMapper.map(castRequest, MoviePersonnelDto.class));
		
		MoviePersonnelResponse castResponse = modelMapper.map(castDto, MoviePersonnelResponse.class);
		
		return new ResponseEntity<MoviePersonnelResponse>(castResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<MoviePersonnelResponseBasic>> getAllCasts(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/casts", "getAllCasts");
		
		if (page > 0) {
			page--;
		}
		
		List<MoviePersonnelDto> castDtos = null;
		
		if (sort.isEmpty()) {
			castDtos = castService.getAll(page, limit);
		} else {
			castDtos = castService.getAll(page, limit, sort, order);
		}
		
		if (castDtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<MoviePersonnelResponseBasic> castResponses = new ArrayList<>();
		castDtos.forEach(castDto -> castResponses.add(modelMapper.map(castDto, MoviePersonnelResponseBasic.class)));
		
		return new ResponseEntity<List<MoviePersonnelResponseBasic>>(castResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<MoviePersonnelResponse> getCastById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/casts/{id}", "getCastById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Cast, path: /api/v1/casts/{id}");
		}
		
		MoviePersonnelDto castDto = castService.getById(id);
		
		if (castDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MoviePersonnelResponse>(modelMapper.map(castDto, MoviePersonnelResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<MoviePersonnelResponse> updateCastById(@Valid @PathVariable Long id, @RequestBody MoviePersonnelRequest castRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/casts/{id}", "updateCastById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Cast, path: /api/v1/casts/{id}");
		}
		
		if (castRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Cast");
		}
		
		MoviePersonnelDto castDto = castService.updateById(id, modelMapper.map(castRequest, MoviePersonnelDto.class));
		
		return new ResponseEntity<MoviePersonnelResponse>(modelMapper.map(castDto, MoviePersonnelResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteCastById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/casts/{id}", "deleteCastById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Cast, path: /api/v1/casts/{id}");
		}
		
		castService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
