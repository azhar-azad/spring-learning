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
import com.azad.estatement.models.Organization;
import com.azad.estatement.models.dtos.UsrDto;
import com.azad.estatement.models.requests.UsrRequest;
import com.azad.estatement.models.responses.UsrResponse;
import com.azad.estatement.models.responses.UsrResponsesWithOrg;
import com.azad.estatement.services.OrgService;
import com.azad.estatement.services.UsrService;
import com.azad.estatement.utils.ApiUtils;
import com.azad.estatement.utils.AppUtils;
import com.azad.estatement.utils.PagingAndSorting;

@RestController
@RequestMapping(path = "/api/v1/{orgName}/usrs")
public class UsrController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiUtils apiUtils;
	
	private UsrService usrService;
	
	@Autowired
	public UsrController(UsrService usrService) {
		this.usrService = usrService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> usrRouteTest(@PathVariable String orgName) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/" + orgName + "/usrs/test", "usrRouteTest");
		
		return ResponseEntity.ok().body("{\"status\": \"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<UsrResponse> createUsr(@PathVariable String orgName, @Valid @RequestBody UsrRequest usrRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/" + orgName + "/usrs", "createUsr");
		
		if (usrRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Usr");
		}
		
		UsrDto usrDto = usrService.create(orgName, modelMapper.map(usrRequest, UsrDto.class));
		
		return new ResponseEntity<UsrResponse>(modelMapper.map(usrDto, UsrResponse.class), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<UsrResponsesWithOrg> getAllUsrs(
			@PathVariable String orgName,
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/" + orgName + "/usrs", "getAllUsrs");
		
		if (page > 0) {
			page--;
		}
		
		PagingAndSorting ps = new PagingAndSorting(page, limit, sort, order);
		
		List<UsrDto> usrDtos = usrService.getAll(orgName, ps);
		
		if (usrDtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<UsrResponse> usrResponses = new ArrayList<>();
		usrDtos.forEach(usrDto -> usrResponses.add(modelMapper.map(usrDto, UsrResponse.class)));
		
		return new ResponseEntity<UsrResponsesWithOrg>(new UsrResponsesWithOrg(usrResponses, apiUtils.getOrgByOrgName(orgName)), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{usrId}")
	public ResponseEntity<UsrResponse> getUsrById(@PathVariable String orgName, @Valid @PathVariable Long usrId) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/" + orgName + "/usrs", "getUsrById");
		
		if (usrId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Usr; METHOD: GET; ID: " + usrId);
		}
		
		UsrDto usrDto = usrService.getById(usrId);
		
		if (usrDto == null) {
			return new ResponseEntity<UsrResponse>(HttpStatus.NOT_FOUND);
		}
		
		UsrResponse usrResponse = modelMapper.map(usrDto, UsrResponse.class);
		usrResponse.setOrg(apiUtils.getOrgByOrgName(orgName));
		
		return new ResponseEntity<UsrResponse>(usrResponse, HttpStatus.OK);
	}
	
	@PutMapping(path = "/{usrId}")
	public ResponseEntity<UsrResponse> updateUsrById(@PathVariable String orgName, @PathVariable Long usrId, @RequestBody UsrRequest usrRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/" + orgName + "/usrs", "updateUsrById");
		
		if (usrId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Usr; METHOD: PUT; ID: " + usrId);
		}
		
		if (usrRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Usr");
		}
		
		UsrDto usrDto = usrService.updateById(usrId, modelMapper.map(usrRequest, UsrDto.class));
		
		UsrResponse usrResponse = modelMapper.map(usrDto, UsrResponse.class);
		usrResponse.setOrg(apiUtils.getOrgByOrgName(orgName));
		
		return new ResponseEntity<UsrResponse>(usrResponse, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{usrId}")
	public ResponseEntity<?> deleteUsrById(@PathVariable String orgName, @Valid @PathVariable Long usrId) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/" + orgName + "/usrs", "deleteUsrById");
		
		if (usrId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Usr; METHOD: DELETE; ID: " + usrId);
		}
		
		usrService.deleteById(usrId);
		
		return ResponseEntity.noContent().build();
	}
}
