package com.azad.supershop.controller;

import com.azad.supershop.common.ApiController;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.category.CategoryDto;
import com.azad.supershop.model.dto.category.CategoryRequest;
import com.azad.supershop.model.dto.category.CategoryResponse;
import com.azad.supershop.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController implements ApiController<CategoryRequest, CategoryResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService service;

    @PostMapping
    @Override
    public ResponseEntity<CategoryResponse> createEntity(@Valid @RequestBody CategoryRequest request) {
        CategoryDto dto = modelMapper.map(request, CategoryDto.class);

        CategoryDto savedDto;
        try {
            savedDto = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(savedDto, CategoryResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<CategoryResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;
        List<CategoryDto> dtosFromService;
        try {
            dtosFromService =
                    service.getAll(new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        if (dtosFromService == null)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, CategoryResponse.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<CategoryResponse> getEntity(@Valid @PathVariable("id") Long id) {
        CategoryDto fetchedDto;
        try {
            fetchedDto = service.getById(id);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(fetchedDto, CategoryResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<CategoryResponse> updateEntity(
            @Valid @PathVariable("id") Long id,
            @RequestBody CategoryRequest updatedRequest) {
        CategoryDto updatedDto;
        try {
            updatedDto = service.updateById(id, modelMapper.map(updatedRequest, CategoryDto.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(updatedDto, CategoryResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        try {
            service.deleteById(id);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Category deleted. id: " + id);
    }
}
