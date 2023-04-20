package com.azad.moneymanagerapi.rest.controllers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.AccountDto;
import com.azad.moneymanagerapi.models.requests.AccountRequest;
import com.azad.moneymanagerapi.models.responses.AccountResponse;
import com.azad.moneymanagerapi.rest.assemblers.AccountModelAssembler;
import com.azad.moneymanagerapi.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountRestController {

    @Autowired
    private ModelMapper modelMapper;

    private final AccountService service;
    private final AccountModelAssembler assembler;

    @Autowired
    public AccountRestController(AccountService service, AccountModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<AccountResponse>> createEntity(@Valid @RequestBody AccountRequest request) {

        AccountDto dto = modelMapper.map(request, AccountDto.class);

        AccountDto savedDtoFromService;
        try {
            savedDtoFromService = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDtoFromService, AccountResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<AccountResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<AccountDto> allDtosFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allDtosFromService == null || allDtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<AccountResponse> responses = allDtosFromService.stream()
                .map(dto -> modelMapper.map(dto, AccountResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AccountResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<AccountResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        AccountDto fetchedDtoFromService = service.getById(id);

        if (fetchedDtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(fetchedDtoFromService, AccountResponse.class)),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<AccountResponse>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody AccountRequest updatedRequest) {

        AccountDto updatedDtoFromService;
        try {
            updatedDtoFromService = service.updateById(id, modelMapper.map(updatedRequest, AccountDto.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(updatedDtoFromService, AccountResponse.class)),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {

        try {
            service.deleteById(id);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
