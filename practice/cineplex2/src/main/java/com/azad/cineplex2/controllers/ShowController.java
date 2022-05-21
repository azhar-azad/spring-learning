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

import com.azad.cineplex2.dto.ShowDto;
import com.azad.cineplex2.dto.requests.ShowRequest;
import com.azad.cineplex2.dto.responses.ShowResponse;
import com.azad.cineplex2.exceptions.InvalidPathVariableException;
import com.azad.cineplex2.exceptions.RequestBodyEmptyException;
import com.azad.cineplex2.services.ShowService;
import com.azad.cineplex2.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/shows")
public class ShowController {

	private ModelMapper modelMapper;
	private ShowService showService;
	
	@Autowired
	public ShowController(ModelMapper modelMapper, ShowService showService) {
		this.modelMapper = modelMapper;
		this.showService = showService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> showRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/shows/test", "showRouteTest");
		
		return ResponseEntity.ok().body("{\"status\":\"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<ShowResponse> createShow(@Valid @RequestBody ShowRequest showRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/shows", "createShow");
		
		if (showRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Show");
		}
		
		ShowDto showDto = showService.create(modelMapper.map(showRequest, ShowDto.class));
			
		return new ResponseEntity<ShowResponse>(modelMapper.map(showDto, ShowResponse.class), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ShowResponse>> getAllShows(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/shows", "getAllShows");
		
		if (page > 0) {
			page--;
		}
		
		List<ShowDto> showDtos = null;
		
		if (sort.isEmpty()) {
			showDtos = showService.getAll(page, limit);
		} else {
			showDtos = showService.getAll(page, limit, sort, order);
		}
		
		if (showDtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<ShowResponse> showResponses = new ArrayList<>();
		showDtos.forEach(showDto -> showResponses.add(modelMapper.map(showDto, ShowResponse.class)));
		
		return new ResponseEntity<List<ShowResponse>>(showResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<ShowResponse> getShowById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/shows/{id}", "getShowById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Show, path: /api/v1/halls/{id}");
		}
		
		ShowDto showDto = showService.getById(id);
		
		if (showDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ShowResponse>(modelMapper.map(showDto, ShowResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<ShowResponse> updateShowById(@Valid @PathVariable Long id, @Valid @RequestBody ShowRequest showRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/shows/{id}", "updateShowById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Show, path: /api/v1/halls/{id}");
		}
		
		if (showRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; entity: Show");
		}
		
		ShowDto showDto = showService.updateById(id, modelMapper.map(showRequest, ShowDto.class));
		
		return new ResponseEntity<ShowResponse>(modelMapper.map(showDto, ShowResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> deleteShowById(@Valid @PathVariable Long id) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/shows/{id}", "deleteShowById");
		
		if (id < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via url parameter; entity: Show, path: /api/v1/halls/{id}");
		}
		
		showService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
