package com.azad.supershop.controller;

import com.azad.supershop.common.ApiController;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.customer.CustomerDto;
import com.azad.supershop.model.dto.customer.CustomerRequest;
import com.azad.supershop.model.dto.customer.CustomerResponse;
import com.azad.supershop.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController implements ApiController<CustomerRequest, CustomerResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerService service;

    @PostMapping
    @Override
    public ResponseEntity<CustomerResponse> createEntity(@Valid @RequestBody CustomerRequest request) {
        CustomerDto dto = modelMapper.map(request, CustomerDto.class);

        CustomerDto savedDto;
        try {
            savedDto = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(savedDto, CustomerResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<CustomerResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<CustomerDto> dtosFromService;
        try {
            dtosFromService = service.getAll(
                    new PagingAndSorting(page > 0 ? page-1 : page, limit, sort, order));
            if (dtosFromService == null)
                return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, CustomerResponse.class))
                .toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResponse> getEntity(Long id) {
        return null;
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<CustomerResponse> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody CustomerRequest updatedRequest) {

        CustomerDto updatedDto;
        try {
            updatedDto = service.updateById(id, modelMapper.map(updatedRequest, CustomerDto.class));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(updatedDto, CustomerResponse.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        try {
            service.deleteById(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Customer deleted. id: " + id);
    }

    @GetMapping(path = "/byPhone/{phone}")
    public ResponseEntity<CustomerResponse> getEntity(@Valid @PathVariable("phone") String phone) {
        CustomerDto fetchedDto;
        try {
            fetchedDto = service.getByPhone(phone);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(fetchedDto, CustomerResponse.class), HttpStatus.OK);
    }

    @GetMapping(path = "/totalDue")
    public ResponseEntity<Map<String, Double>> getTotalDue() {

        Double totalDue;
        try {
            totalDue = service.getTotalDueForAll();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(Collections.singletonMap("Total due of all customers", totalDue),
                HttpStatus.OK);
    }
}
