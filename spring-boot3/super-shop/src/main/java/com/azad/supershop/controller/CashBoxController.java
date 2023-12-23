package com.azad.supershop.controller;

import com.azad.supershop.common.ApiController;
import com.azad.supershop.model.dto.cashbox.CashBoxDto;
import com.azad.supershop.model.dto.cashbox.CashBoxRequest;
import com.azad.supershop.model.dto.cashbox.CashBoxResponse;
import com.azad.supershop.service.CashBoxService;
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
@RequestMapping(path = "/api/v1/cashBoxes")
public class CashBoxController implements ApiController<CashBoxRequest, CashBoxResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CashBoxService service;

    @PostMapping
    @Override
    public ResponseEntity<CashBoxResponse> createEntity(@Valid @RequestBody CashBoxRequest request) {

        CashBoxDto dto = modelMapper.map(request, CashBoxDto.class);

        CashBoxDto savedDto;
        try {
            savedDto = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(savedDto, CashBoxResponse.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CashBoxResponse>> getAllEntity(int page, int limit, String sort, String order) {
        return null;
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<CashBoxResponse> getEntity(@Valid @PathVariable("id") Long id) {

        CashBoxDto fetchedDto;
        try {
            fetchedDto = service.getById(id);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(fetchedDto, CashBoxResponse.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CashBoxResponse> updateEntity(Long id, CashBoxRequest updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }

    @GetMapping(path = "/byName/{boxName}")
    public ResponseEntity<CashBoxResponse> getEntity(@Valid @PathVariable("boxName") String boxName) {

        CashBoxDto fetchedDto;
        try {
            fetchedDto = service.getByName(boxName);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(fetchedDto, CashBoxResponse.class), HttpStatus.OK);
    }

    @GetMapping(path = "/total")
    public ResponseEntity<Map<String, Double>> getTotalAmount() {

        Double totalAmount;
        try {
            totalAmount = service.getTotalAmount();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(Collections.singletonMap("Total Amount", totalAmount), HttpStatus.OK);
    }
}
