package com.azad.cineplex.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azad.cineplex.dto.HallDTO;
import com.azad.cineplex.exceptions.RequestBodyEmptyException;
import com.azad.cineplex.exceptions.ResourceCreationFailedException;
import com.azad.cineplex.models.requests.HallRequest;
import com.azad.cineplex.models.responses.HallResponse;
import com.azad.cineplex.services.HallService;
import com.azad.cineplex.utility.Utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(path = "/api/v1/halls")
@Data
@RequiredArgsConstructor
public class HallController {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private HallService hallService;
	
	@Autowired
	public HallController(HallService hallService) {
		this.hallService = hallService;
	}

	@GetMapping(path = "/test")
	public String hallRouteTest() {
		
		Utils.printControllerMethodInfo("GET", "/api/v1/halls/test", "test");
		
		return "/api/v1/halls is OK";
	}
	
	@PostMapping
	public ResponseEntity<HallResponse> createHall(@Valid @RequestBody HallRequest hallRequest) {
		
		Utils.printControllerMethodInfo("POST", "/api/v1/halls", "createHall");
		
		if (hallRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty :::: entity: Hall");
		}
		
		HallDTO hallDTO = hallService.create(modelMapper.map(hallRequest, HallDTO.class));
		
		if (hallDTO == null) {
			throw new ResourceCreationFailedException("Failed to create a new hall");
		}
		
		HallResponse hallResponse = modelMapper.map(hallDTO, HallResponse.class);
		
		return new ResponseEntity<HallResponse>(hallResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<HallResponse>> getAllHalls(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		Utils.printControllerMethodInfo("GET", "/api/v1/halls", "getAllHalls");
		
		if (page > 0) {
			page--;
		}
		
		List<HallDTO> hallDTOs = null;
		
		if (sort.isEmpty()) {
			hallDTOs = hallService.getAll(page, limit);
		} else {
			hallDTOs = hallService.getAll(page, limit, sort, order);
		}
		
		if (hallDTOs == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<HallResponse> hallResponses = new ArrayList<>();
		hallDTOs.forEach(hallDTO -> hallResponses.add(modelMapper.map(hallDTO, HallResponse.class)));
		
		return new ResponseEntity<List<HallResponse>>(hallResponses, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
