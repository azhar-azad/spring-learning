package com.azad.bankapi.controllers;

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

import com.azad.bankapi.exceptions.InvalidPathVariableException;
import com.azad.bankapi.exceptions.RequestBodyEmptyException;
import com.azad.bankapi.models.Address;
import com.azad.bankapi.models.dtos.BankUserDto;
import com.azad.bankapi.models.requests.BankUserRequest;
import com.azad.bankapi.models.responses.BankUserResponse;
import com.azad.bankapi.services.BankUserService;
import com.azad.bankapi.utils.AppUtils;
import com.azad.bankapi.utils.PagingAndSorting;

@RestController
@RequestMapping(path = "/api/v1/bankUsers")
public class BankUserController {
	
	@Autowired
	private AppUtils appUtils;
	
	@Autowired
	private ModelMapper mapper;

	private BankUserService bankUserService;

	@Autowired
	public BankUserController(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}
	
	@PostMapping
	public ResponseEntity<BankUserResponse> createBankUser(@Valid @RequestBody BankUserRequest requestBody) {
		
		appUtils.printControllerMethodInfo("POST", "/api/v1/bankUsers", "createBankUser");
		
		if (requestBody == null) {
			throw new RequestBodyEmptyException("Request body is empty. Entity: BankUser");
		}
		
		Address address = new Address(requestBody.getHouse(), requestBody.getRoad(), requestBody.getArea(), requestBody.getCity(), 
				requestBody.getUpazila(), requestBody.getDistrict());
		
		BankUserDto dto = mapper.map(requestBody, BankUserDto.class); 
		dto.setAddress(address);
		
		return new ResponseEntity<BankUserResponse>(mapper.map(bankUserService.create(dto), BankUserResponse.class), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<BankUserResponse>> getAllBankUsers(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page,
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort,
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		appUtils.printControllerMethodInfo("GET", "/api/v1/bankUsers", "getAllBankUsers");
		
		if (page > 0) {
			page--;
		}
		
		PagingAndSorting ps = new PagingAndSorting(page, limit, sort, order);
		
		List<BankUserDto> dtos = bankUserService.getAll(ps);
		
		if (dtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<BankUserResponse> responses = new ArrayList<>();
		dtos.forEach(dto -> responses.add(mapper.map(dto, BankUserResponse.class)));
		
		return new ResponseEntity<List<BankUserResponse>>(responses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{bankUserId}")
	public ResponseEntity<BankUserResponse> getBankUserById(@Valid @PathVariable Long bankUserId) {
		
		appUtils.printControllerMethodInfo("GET", "/api/v1/bankUsers/{bankUserId}", "getBankUserById");
		
		if (bankUserId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter. Entity: BankUser. Method: GET. Id: " + bankUserId);
		}
		
		BankUserDto dto = bankUserService.getById(bankUserId);
		
		if (dto == null) {
			return new ResponseEntity<BankUserResponse>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<BankUserResponse>(mapper.map(dto, BankUserResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{bankUserId}")
	public ResponseEntity<BankUserResponse> updateBankUserById(@Valid @PathVariable Long bankUserId, @RequestBody BankUserRequest requestBody) {
		
		appUtils.printControllerMethodInfo("PUT", "/api/v1/bankUsers/{bankUserId}", "updateBankUserById");
		
		if (bankUserId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter. Entity: BankUser. Method: GET. Id: " + bankUserId);
		}
		
		if (requestBody == null) {
			throw new RequestBodyEmptyException("Request body is empty. Entity: BankUser");
		}
		
		Address address = new Address(requestBody.getHouse(), requestBody.getRoad(), requestBody.getArea(), requestBody.getCity(), 
				requestBody.getUpazila(), requestBody.getDistrict());
		
		BankUserDto dto = mapper.map(requestBody, BankUserDto.class); 
		dto.setAddress(address);
		
		BankUserDto updatedDto = bankUserService.updateById(bankUserId, dto);
		
		if (updatedDto == null) {
			return new ResponseEntity<BankUserResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BankUserResponse>(mapper.map(updatedDto, BankUserResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{bankUserId}")
	public ResponseEntity<?> deleteBankUserById(@Valid @PathVariable Long bankUserId) {
		
		appUtils.printControllerMethodInfo("DELETE", "/api/v1/bankUsers/{bankUserId}", "deleteBankUserById");
		
		if (bankUserId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter. Entity: BankUser. Method: GET. Id: " + bankUserId);
		}
		
		bankUserService.deleteById(bankUserId);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
