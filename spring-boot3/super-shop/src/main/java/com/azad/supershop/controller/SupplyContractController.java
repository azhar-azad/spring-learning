package com.azad.supershop.controller;

import com.azad.supershop.common.ApiController;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.supplycontract.SupplyContractDto;
import com.azad.supershop.model.dto.supplycontract.SupplyContractRequest;
import com.azad.supershop.model.dto.supplycontract.SupplyContractResponse;
import com.azad.supershop.service.SupplyContractService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/supplyContracts")
public class SupplyContractController implements ApiController<SupplyContractRequest, SupplyContractResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SupplyContractService service;

    @PostMapping
    @Override
    public ResponseEntity<SupplyContractResponse> createEntity(@Valid @RequestBody SupplyContractRequest request) {
        SupplyContractDto dto = modelMapper.map(request, SupplyContractDto.class);
        SupplyContractDto savedDto;
        try {
            savedDto = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(modelMapper.map(savedDto, SupplyContractResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<SupplyContractResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {
        if (page < 0) page = 0;
        List<SupplyContractDto> dtosFromService;
        try {
            dtosFromService = service.getAll(
                    new PagingAndSorting(page > 0 ? page-1 : page, limit, sort, order));
            if (dtosFromService == null)
                return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, SupplyContractResponse.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<SupplyContractResponse> getEntity(@Valid @PathVariable("id") Long id) {
        SupplyContractDto dto;
        try {
            dto = service.getById(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(modelMapper.map(dto, SupplyContractResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<SupplyContractResponse> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody SupplyContractRequest updatedRequest) {
        SupplyContractDto dto;
        try {
            dto = service.updateById(id, modelMapper.map(updatedRequest, SupplyContractDto.class));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(modelMapper.map(dto, SupplyContractResponse.class), HttpStatus.OK);
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

    @GetMapping(path = "/bySupplier/{supplierId}")
    public ResponseEntity<List<SupplyContractResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order,
            @Valid @PathVariable("supplierId") Long supplierId) {
        if (page < 0) page = 0;
        List<SupplyContractDto> dtosFromService;
        try {
            dtosFromService = service.getAllBySupplier(supplierId,
                    new PagingAndSorting(page > 0 ? page-1 : page, limit, sort, order));
            if (dtosFromService == null)
                return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, SupplyContractResponse.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/byDate/{contractDate}")
    public ResponseEntity<List<SupplyContractResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order,
            @Valid @PathVariable("contractDate") LocalDate contractDate) {
        if (page < 0) page = 0;
        List<SupplyContractDto> dtosFromService;
        try {
            dtosFromService = service.getAllByContractDate(contractDate,
                    new PagingAndSorting(page > 0 ? page-1 : page, limit, sort, order));
            if (dtosFromService == null)
                return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, SupplyContractResponse.class))
                .toList(), HttpStatus.OK);
    }
}
