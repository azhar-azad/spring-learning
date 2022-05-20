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

import com.azad.cineplex2.dto.HallDto;
import com.azad.cineplex2.dto.requests.HallRequest;
import com.azad.cineplex2.dto.responses.HallResponse;
import com.azad.cineplex2.exceptions.InvalidPathVariableException;
import com.azad.cineplex2.exceptions.RequestBodyEmptyException;
import com.azad.cineplex2.services.HallService;
import com.azad.cineplex2.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/halls")
public class HallController {

	private ModelMapper modelMapper;
	private HallService hallService;
	
	@Autowired
	public HallController(ModelMapper modelMapper, HallService hallService) {
		super();
		this.modelMapper = modelMapper;
		this.hallService = hallService;
	}

	@GetMapping(path = "/test")
	public ResponseEntity<String> hallRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/halls/test", "test");
		
		return ResponseEntity.ok().body("{\"status\":\"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<HallResponse> createHall(@Valid @RequestBody HallRequest hallRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/halls", "createHall");
		
		if (hallRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Hall");
		}
		
		HallDto hallDto = hallService.create(modelMapper.map(hallRequest, HallDto.class));
		
		HallResponse hallResponse = modelMapper.map(hallDto, HallResponse.class);
		
		return new ResponseEntity<HallResponse>(hallResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<HallResponse>> getAllHalls(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/halls", "getAllHalls");
		
		if (page > 0) {
			page--;
		}
		
		List<HallDto> hallDtos = null;
		
		if (sort.isEmpty()) {
			hallDtos = hallService.getAll(page, limit);
		} else {
			hallDtos = hallService.getAll(page, limit, sort, order);
		}
		
		if (hallDtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<HallResponse> hallResponses = new ArrayList<>();
		hallDtos.forEach(hallDto -> hallResponses.add(modelMapper.map(hallDto, HallResponse.class)));
		
		return new ResponseEntity<List<HallResponse>>(hallResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<HallResponse> getHallById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/halls/{id}", "getHallById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Hall, path: /api/v1/halls/{id}");
		}
		
		HallDto hallDto = hallService.getById(id);
		
		if (hallDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<HallResponse>(modelMapper.map(hallDto, HallResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<HallResponse> updateHallById(@Valid @PathVariable Long id, @Valid @RequestBody HallRequest hallRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/halls/{id}", "updateHallById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Hall, path: /api/v1/halls/{id}");
		}
		
		if (hallRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Hall");
		}
		
		HallDto hallDto = hallService.updateById(id, modelMapper.map(hallRequest, HallDto.class));
		
		return new ResponseEntity<HallResponse>(modelMapper.map(hallDto, HallResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity deleteHallById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/halls/{id}", "deleteHallById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Hall, path: /api/v1/halls/{id}");
		}
		
		hallService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
