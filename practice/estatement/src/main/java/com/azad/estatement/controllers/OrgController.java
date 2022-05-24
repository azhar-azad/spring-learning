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
import com.azad.estatement.models.dtos.OrgDto;
import com.azad.estatement.models.requests.OrgRequest;
import com.azad.estatement.models.responses.OrgResponse;
import com.azad.estatement.services.OrgService;
import com.azad.estatement.utils.AppUtils;

@RestController
@RequestMapping(path = "/api/v1/organizations")
public class OrgController {

	private ModelMapper modelMapper;
	
	private OrgService orgService;

	@Autowired
	public OrgController(ModelMapper modelMapper, OrgService orgService) {
		this.modelMapper = modelMapper;
		this.orgService = orgService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> orgRouteTest() {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/organizations/test", "orgRouteTest");
		
		return ResponseEntity.ok().body("{\"status\": \"OK\"}");
	}
	
	@PostMapping
	public ResponseEntity<OrgResponse> createOrg(@Valid @RequestBody OrgRequest orgRequest) {
		
		AppUtils.printControllerMethodInfo("POST", "/api/v1/organizations", "createOrg");
		
		if (orgRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Organization");
		}
		
		OrgDto orgDto = orgService.create(modelMapper.map(orgRequest, OrgDto.class));
		
		return new ResponseEntity<OrgResponse>(modelMapper.map(orgDto, OrgResponse.class), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<OrgResponse>> getAllOrgs(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/organizations", "getAllOrgs");
		
		if (page > 0) {
			page--;
		}
		
		List<OrgDto> orgDtos = null;
		
		if (sort.isEmpty()) {
			orgDtos = orgService.getAll(page, limit);
		} else {
			orgDtos = orgService.getAll(page, limit, sort, order);
		}
		
		if (orgDtos == null) {
			return new ResponseEntity<List<OrgResponse>>(HttpStatus.NO_CONTENT);
		}
		
		List<OrgResponse> orgResponses = new ArrayList<>();
		orgDtos.forEach(orgDto -> orgResponses.add(modelMapper.map(orgDto, OrgResponse.class)));
		
		return new ResponseEntity<List<OrgResponse>>(orgResponses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{orgId}")
	public ResponseEntity<OrgResponse> getOrgById(@Valid @PathVariable Long orgId) {
		
		AppUtils.printControllerMethodInfo("GET", "/api/v1/organizations/{orgId}", "getOrgById");
		
		if (orgId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Organization; METHOD: GET; ID: " + orgId);
		}
		
		OrgDto orgDto = orgService.getById(orgId);
		
		if (orgDto == null) {
			return new ResponseEntity<OrgResponse>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<OrgResponse>(modelMapper.map(orgDto, OrgResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{orgId}")
	public ResponseEntity<OrgResponse> updateOrgById(@PathVariable Long orgId, @RequestBody OrgRequest orgRequest) {
		
		AppUtils.printControllerMethodInfo("PUT", "/api/v1/organizations/{orgId}", "updateOrgById");
		
		if (orgId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Organization; METHOD: PUT; ID: " + orgId);
		}
		
		if (orgRequest == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Organization");
		}
		
		OrgDto orgDto = orgService.updateById(orgId, modelMapper.map(orgRequest, OrgDto.class));
		
		return new ResponseEntity<OrgResponse>(modelMapper.map(orgDto, OrgResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{orgId}")
	public ResponseEntity<?> deleteOrgById(@Valid @PathVariable Long orgId) {
		
		AppUtils.printControllerMethodInfo("DELETE", "/api/v1/organizations/{orgId}", "deleteOrgById");
		
		if (orgId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Organization; METHOD: DELETE; ID: " + orgId);
		}
		
		orgService.deleteById(orgId);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
