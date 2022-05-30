package com.azad.bankapi.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
import com.azad.bankapi.models.dtos.AccountDto;
import com.azad.bankapi.models.requests.AccountRequest;
import com.azad.bankapi.models.responses.AccountResponse;
import com.azad.bankapi.services.AccountService;
import com.azad.bankapi.utils.AppUtils;
import com.azad.bankapi.utils.PagingAndSorting;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountRestController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AppUtils appUtils;

	private AccountService accountService;

	@Autowired
	public AccountRestController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping(path = "/test")
	public ResponseEntity<String> accountsRouteTest() {
		
		appUtils.printControllerMethodInfo("GET", "/api/v1/accounts/test", "accountsRouteTest");
		
		return ResponseEntity.ok().body("/api/v1/accounts/test is serving");
	}
	
	@PostMapping
	public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
		
		appUtils.printControllerMethodInfo("GET", "/api/v1/accounts", "createAccount");
		
		if (request == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Account");
		}
		
		AccountDto dto = accountService.create(modelMapper.map(request, AccountDto.class));
		
		return new ResponseEntity<AccountResponse>(modelMapper.map(dto, AccountResponse.class), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountResponse>> getAllAccounts(
			@Valid @RequestParam(value = "page", defaultValue = "1") int page, 
			@Valid @RequestParam(value = "limit", defaultValue = "25") int limit, 
			@Valid @RequestParam(value = "sort", defaultValue = "") String sort, 
			@Valid @RequestParam(value = "order", defaultValue = "asc") String order) {
		
		appUtils.printControllerMethodInfo("GET", "/api/v1/accounts", "getAllAccounts");
		
		if (page > 0) {
			page--;
		}
		
		PagingAndSorting ps = new PagingAndSorting(page, limit, sort, order);
		
		List<AccountDto> dtos = accountService.getAll(ps);
		
		if (dtos == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<AccountResponse> responses = new ArrayList<>();
		dtos.forEach(dto -> responses.add(modelMapper.map(dto, AccountResponse.class)));
		
		return new ResponseEntity<List<AccountResponse>>(responses, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{acctId}")
	public ResponseEntity<AccountResponse> getAccountById(@Valid @PathVariable Long acctId) {
		
		appUtils.printControllerMethodInfo("GET", "/api/v1/accounts/{acctId}", "getAccountById");
		
		if (acctId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Account; METHOD: GET; ID: " + acctId);
		}
		
		AccountDto dto = accountService.getById(acctId);
		
		if (dto == null) {
			return new ResponseEntity<AccountResponse>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AccountResponse>(modelMapper.map(dto, AccountResponse.class), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{acctId}")
	public ResponseEntity<AccountResponse> updateAccountById(@Valid @PathVariable Long acctId, @RequestBody AccountRequest request) {
		
		appUtils.printControllerMethodInfo("PUT", "/api/v1/accounts/{acctId}", "updateAccountById");
		
		if (acctId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Account; METHOD: GET; ID: " + acctId);
		}
		
		if (request == null) {
			throw new RequestBodyEmptyException("Request body is empty; ENTITY: Account");
		}
		
		AccountDto dto = accountService.updateById(acctId, modelMapper.map(request, AccountDto.class));
		
		return new ResponseEntity<AccountResponse>(modelMapper.map(dto, AccountResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{acctId}")
	public ResponseEntity<?> deleteById(@Valid @PathVariable Long acctId) {
		
		appUtils.printControllerMethodInfo("DELETE", "/api/v1/accounts/{acctId}", "deleteById");
		
		if (acctId < 1) {
			throw new InvalidPathVariableException("Invalid resource id value passed via URL parameter; ENTITY: Account; METHOD: GET; ID: " + acctId);
		}
		
		accountService.deleteById(acctId);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
