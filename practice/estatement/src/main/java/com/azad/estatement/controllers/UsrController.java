package com.azad.estatement.controllers;

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

import com.azad.estatement.exceptions.InvalidPathVariableException;
import com.azad.estatement.exceptions.RequestBodyEmptyException;
import com.azad.estatement.models.dtos.UsrDto;
import com.azad.estatement.models.requests.UsrRequest;
import com.azad.estatement.models.responses.UsrResponse;
import com.azad.estatement.services.UsrService;
import com.azad.estatement.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/usrs")
public class UsrController {

	private ModelMapper modelMapper;
	private UsrService usrService;
	
	@Autowired
	public UsrController(ModelMapper modelMapper, UsrService usrService) {
		this.modelMapper = modelMapper;
		this.usrService = usrService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> usrRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/usrs/test", "usrRouteTest");
		
		return ResponseEntity.ok().body("{\"status\": \"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<UsrResponse> createUsr(@Valid @RequestBody UsrRequest usrRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/usrs", "createUsr");
		
		if (usrRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Usr");
		}
		
		UsrDto usrDto = usrService.create(modelMapper.map(usrRequest, UsrDto.class));
		
		return new ResponseEntity<UsrResponse>(modelMapper.map(usrDto, UsrResponse.class), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<UsrResponse>> getAllUsrs(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/usrs", "getAllUsrs");
		
		if (page > 0) {
			page--;
		}
		
		List<UsrDto> usrDtos = null;
		
		if (sort.isEmpty()) {
			usrDtos = usrService.getAll(page, limit);
		} else {
			usrDtos = usrService.getAll(page, limit, sort, order);
		}
		
		if (usrDtos == null) {
			return new ResponseEntity<List<UsrResponse>>(HttpStatus.NO_CONTENT);
		}
		
		List<UsrResponse> usrResponses = new ArrayList<>();
		usrDtos.forEach(usrDto -> usrResponses.add(modelMapper.map(usrDto, UsrResponse.class)));
		
		return new ResponseEntity<List<UsrResponse>>(usrResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{usrId}")
	public ResponseEntity<UsrResponse> getUsrById(@Valid @PathVariable Long usrId) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/usrs/{usrId}", "getUsrById");
		
		if (usrId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Usr; METHOD: GET; ID: " + usrId);
		}
		
		UsrDto usrDto = usrService.getById(usrId);
		
		if (usrDto == null) {
			return new ResponseEntity<UsrResponse>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<UsrResponse>(modelMapper.map(usrDto, UsrResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{usrId}")
	public ResponseEntity<UsrResponse> updateUsrById(@PathVariable Long usrId, @RequestBody UsrRequest usrRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/usrs/{usrId}", "updateUsrById");
		
		if (usrId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Usr; METHOD: PUT; ID: " + usrId);
		}
		
		if (usrRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Usr");
		}
		
		UsrDto usrDto = usrService.updateById(usrId, modelMapper.map(usrRequest, UsrDto.class));
		
		return new ResponseEntity<UsrResponse>(modelMapper.map(usrDto, UsrResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{usrId}")
	public ResponseEntity<?> deleteUsrById(@Valid @PathVariable Long usrId) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/usrs/{usrId}", "deleteUsrById");
		
		if (usrId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Usr; METHOD: DELETE; ID: " + usrId);
		}
		
		usrService.deleteById(usrId);
		
		return ResponseEntity.noContent().build();
	}
}
