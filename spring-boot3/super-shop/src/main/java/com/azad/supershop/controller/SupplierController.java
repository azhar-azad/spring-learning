package com.azad.supershop.controller;

import com.azad.supershop.common.ApiController;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.supplier.SupplierDto;
import com.azad.supershop.model.dto.supplier.SupplierRequest;
import com.azad.supershop.model.dto.supplier.SupplierResponse;
import com.azad.supershop.service.SupplierService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
public class SupplierController implements ApiController<SupplierRequest, SupplierResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SupplierService service;

    @PostMapping
    @Override
    public ResponseEntity<SupplierResponse> createEntity(@Valid @RequestBody SupplierRequest request) {
        SupplierDto dto = modelMapper.map(request, SupplierDto.class);

        SupplierDto savedDto;
        try {
            savedDto = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(savedDto, SupplierResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<SupplierResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;
        List<SupplierDto> dtosFromService;
        try {
            dtosFromService =
                    service.getAll(new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        if (dtosFromService == null)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, SupplierResponse.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<SupplierResponse> getEntity(@Valid @PathVariable("id") Long id) {
        SupplierDto fetchedDto;
        try {
            fetchedDto = service.getById(id);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(fetchedDto, SupplierResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<SupplierResponse> updateEntity(
            @Valid @PathVariable("id") Long id,
            @RequestBody SupplierRequest updatedRequest) {
        SupplierDto updatedDto;
        try {
            updatedDto = service.updateById(id, modelMapper.map(updatedRequest, SupplierDto.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(updatedDto, SupplierResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        try {
            service.deleteById(id);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Supplier deleted. id: " + id);
    }

    @GetMapping(path = "/byPhone/{phone}")
    public ResponseEntity<SupplierResponse> getEntity(@Valid @PathVariable("phone") String phone) {
        SupplierDto fetchedDto;
        try {
            fetchedDto = service.getByPhone(phone);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(fetchedDto, SupplierResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/byPhone/{phone}")
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("phone") String phone) {
        try {
            service.deleteByPhone(phone);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Supplier deleted. phone: " + phone);
    }
}
