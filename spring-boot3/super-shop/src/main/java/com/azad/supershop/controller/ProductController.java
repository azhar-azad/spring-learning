package com.azad.supershop.controller;

import com.azad.supershop.common.ApiController;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.product.ProductDto;
import com.azad.supershop.model.dto.product.ProductRequest;
import com.azad.supershop.model.dto.product.ProductResponse;
import com.azad.supershop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController implements ApiController<ProductRequest, ProductResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService service;

    @PostMapping
    @Override
    public ResponseEntity<ProductResponse> createEntity(@Valid @RequestBody ProductRequest request) {
        ProductDto dto = modelMapper.map(request, ProductDto.class);
        ProductDto savedDto;
        try {
            savedDto = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(modelMapper.map(savedDto, ProductResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ProductResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {
        if (page < 0) page = 0;
        List<ProductDto> dtosFromService;
        try {
            dtosFromService = service.getAll(
                    new PagingAndSorting(page > 0 ? page-1 : page, limit, sort, order));
            if (dtosFromService == null)
                return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, ProductResponse.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<ProductResponse> getEntity(@Valid @PathVariable("id") Long id) {
        ProductDto dto;
        try {
            dto = service.getById(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(modelMapper.map(dto, ProductResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<ProductResponse> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody ProductRequest updatedRequest) {
        ProductDto dto;
        try {
            dto = service.updateById(id, modelMapper.map(updatedRequest, ProductDto.class));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(modelMapper.map(dto, ProductResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        try {
            service.deleteById(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Product deleted. id: " + id);
    }

    @GetMapping(path = "/byCategory/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order,
            @Valid @PathVariable("categoryId") Long categoryId) {
        if (page < 0) page = 0;
        List<ProductDto> dtosFromService;
        try {
            dtosFromService = service.getAllByCategory(categoryId,
                    new PagingAndSorting(page > 0 ? page-1 : page, limit, sort, order));
            if (dtosFromService == null)
                return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, ProductResponse.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/search/{productName}")
    public ResponseEntity<ProductResponse> getEntity(@Valid @PathVariable("productName") String productName) {
        ProductDto dto;
        try {
            dto = service.getByProductName(productName);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(modelMapper.map(dto, ProductResponse.class), HttpStatus.OK);
    }
}
