package com.azad.moneymanagerapi.rest.controllers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.CategoryDto;
import com.azad.moneymanagerapi.models.requests.CategoryRequest;
import com.azad.moneymanagerapi.models.responses.CategoryResponse;
import com.azad.moneymanagerapi.rest.assemblers.CategoryModelAssembler;
import com.azad.moneymanagerapi.services.CategoryService;
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
@RequestMapping(path = "/api/v1/categories")
public class CategoryRestController implements GenericApiRestController<CategoryRequest, CategoryResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final CategoryService service;
    private final CategoryModelAssembler assembler;

    @Autowired
    public CategoryRestController(CategoryService service, CategoryModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> createEntity(@Valid @RequestBody CategoryRequest request) {

        CategoryDto dto = modelMapper.map(request, CategoryDto.class);

        CategoryDto savedDtoFromService = service.create(dto);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDtoFromService, CategoryResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<CategoryResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<CategoryDto> allDtosFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allDtosFromService == null || allDtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<CategoryResponse> responses = allDtosFromService.stream()
                .map(dto -> modelMapper.map(dto, CategoryResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CategoryResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        CategoryDto fetchedDtoFromService = service.getById(id);

        if (fetchedDtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(fetchedDtoFromService, CategoryResponse.class)),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody CategoryRequest updatedRequest) {

        CategoryDto updatedDtoFromService;
        try {
            updatedDtoFromService = service.updateById(id, modelMapper.map(updatedRequest, CategoryDto.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(updatedDtoFromService, CategoryResponse.class)),
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
