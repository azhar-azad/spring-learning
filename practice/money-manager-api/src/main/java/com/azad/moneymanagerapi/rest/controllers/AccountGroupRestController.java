package com.azad.moneymanagerapi.rest.controllers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.AccountGroupDto;
import com.azad.moneymanagerapi.models.requests.AccountGroupRequest;
import com.azad.moneymanagerapi.models.responses.AccountGroupResponse;
import com.azad.moneymanagerapi.rest.assemblers.AccountGroupModelAssembler;
import com.azad.moneymanagerapi.services.AccountGroupService;
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
@RequestMapping(path = "/api/v1/accountGroups")
public class AccountGroupRestController implements GenericApiRestController<AccountGroupRequest, AccountGroupResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final AccountGroupService service;
    private final AccountGroupModelAssembler assembler;

    @Autowired
    public AccountGroupRestController(AccountGroupService service, AccountGroupModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<AccountGroupResponse>> createEntity(@Valid @RequestBody AccountGroupRequest request) {

        AccountGroupDto dto = modelMapper.map(request, AccountGroupDto.class);

        AccountGroupDto savedDtoFromService = service.create(dto);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDtoFromService, AccountGroupResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<AccountGroupResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<AccountGroupDto> allAccountGroupsFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allAccountGroupsFromService == null || allAccountGroupsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<AccountGroupResponse> responses = allAccountGroupsFromService.stream()
                .map(dto -> modelMapper.map(dto, AccountGroupResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AccountGroupResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<AccountGroupResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        AccountGroupDto fetchedDtoFromService = service.getById(id);

        if (fetchedDtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(fetchedDtoFromService, AccountGroupResponse.class)),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<AccountGroupResponse>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody AccountGroupRequest updatedRequest) {

        AccountGroupDto updatedDtoFromService;
        try {
            updatedDtoFromService = service.updateById(id, modelMapper.map(updatedRequest, AccountGroupDto.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(updatedDtoFromService, AccountGroupResponse.class)),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {

        try {
            service.deleteById(id);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
