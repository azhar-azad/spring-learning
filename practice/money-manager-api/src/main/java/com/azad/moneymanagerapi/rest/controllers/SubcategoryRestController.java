package com.azad.moneymanagerapi.rest.controllers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.AccountDto;
import com.azad.moneymanagerapi.models.dtos.SubcategoryDto;
import com.azad.moneymanagerapi.models.requests.AccountRequest;
import com.azad.moneymanagerapi.models.requests.SubcategoryRequest;
import com.azad.moneymanagerapi.models.responses.AccountResponse;
import com.azad.moneymanagerapi.models.responses.SubcategoryResponse;
import com.azad.moneymanagerapi.rest.assemblers.SubcategoryModelAssembler;
import com.azad.moneymanagerapi.services.SubcategoryService;
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
@RequestMapping(path = "/api/v1/subcategories")
public class SubcategoryRestController {

    @Autowired
    private ModelMapper modelMapper;

    private final SubcategoryService service;
    private final SubcategoryModelAssembler assembler;

    @Autowired
    public SubcategoryRestController(SubcategoryService service, SubcategoryModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SubcategoryResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<SubcategoryDto> allDtosFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allDtosFromService == null || allDtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<SubcategoryResponse> responses = allDtosFromService.stream()
                .map(dto -> modelMapper.map(dto, SubcategoryResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<SubcategoryResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<SubcategoryResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        SubcategoryDto fetchedDtoFromService = service.getById(id);

        if (fetchedDtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(fetchedDtoFromService, SubcategoryResponse.class)),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<SubcategoryResponse>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody SubcategoryRequest updatedRequest) {

        SubcategoryDto updatedDtoFromService;
        try {
            updatedDtoFromService = service.updateById(id, modelMapper.map(updatedRequest, SubcategoryDto.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(updatedDtoFromService, SubcategoryResponse.class)),
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
