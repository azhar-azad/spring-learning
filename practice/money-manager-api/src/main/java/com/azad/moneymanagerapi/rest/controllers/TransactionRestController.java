package com.azad.moneymanagerapi.rest.controllers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.TransactionDto;
import com.azad.moneymanagerapi.models.requests.TransactionRequest;
import com.azad.moneymanagerapi.models.responses.TransactionResponse;
import com.azad.moneymanagerapi.rest.assemblers.TransactionModelAssembler;
import com.azad.moneymanagerapi.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionRestController implements GenericApiRestController<TransactionRequest, TransactionResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final TransactionService service;
    private final TransactionModelAssembler assembler;

    @Autowired
    public TransactionRestController(TransactionService service, TransactionModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<TransactionResponse>> createEntity(@Valid @RequestBody TransactionRequest request) {

        TransactionDto dto = modelMapper.map(request, TransactionDto.class);

        TransactionDto savedDtoFromService;
        try {
            savedDtoFromService = service.create(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDtoFromService, TransactionResponse.class)),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<TransactionResponse>>> getAllEntities(int page, int limit, String sort, String order) {
        return null;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TransactionResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order,
            @Valid @RequestParam(value = "transactionType") Optional<String> transactionTypeOption) {

        if (page < 0) page = 0;

        List<TransactionDto> savedDtosFromService;
        if (transactionTypeOption.isPresent()) {
            savedDtosFromService = service.getAllByTransactionType(
                    new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order), transactionTypeOption.get());
        } else {
            savedDtosFromService = service.getAll(
                    new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        }

        if (savedDtosFromService == null || savedDtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<TransactionResponse> responses = savedDtosFromService.stream()
                .map(dto -> modelMapper.map(dto, TransactionResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TransactionResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TransactionResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        TransactionDto fetchedDtoFromService = service.getById(id);

        if (fetchedDtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(fetchedDtoFromService, TransactionResponse.class)),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TransactionResponse>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody TransactionRequest updatedRequest) {

        TransactionDto updatedDtoFromService;
        try {
            updatedDtoFromService = service.updateById(id, modelMapper.map(updatedRequest, TransactionDto.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(updatedDtoFromService, TransactionResponse.class)),
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
