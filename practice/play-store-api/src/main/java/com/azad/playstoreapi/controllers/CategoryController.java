package com.azad.playstoreapi.controllers;

import com.azad.playstoreapi.models.dtos.CategoryDto;
import com.azad.playstoreapi.models.pojos.Category;
import com.azad.playstoreapi.models.responses.CategoryResponse;
import com.azad.playstoreapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    @Autowired
    private ModelMapper modelMapper;

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody Category category) {

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        CategoryDto savedDto = service.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedDto, CategoryResponse.class), HttpStatus.CREATED);
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<List<CategoryResponse>> createCategoryBatch(@RequestBody List<Category> categories) {
        List<CategoryDto> dtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        List<CategoryDto> savedDtos = dtos.stream().map(service::create).collect(Collectors.toList());

        return new ResponseEntity<>(
                savedDtos.stream()
                        .map(savedDto -> modelMapper.map(savedDto, CategoryResponse.class))
                        .collect(Collectors.toList()),
                HttpStatus.CREATED);
    }
}
