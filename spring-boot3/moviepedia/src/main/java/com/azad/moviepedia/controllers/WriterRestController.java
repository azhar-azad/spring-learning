package com.azad.moviepedia.controllers;

import com.azad.moviepedia.assemblers.WriterModelAssembler;
import com.azad.moviepedia.common.ApiRestController;
import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.LogUtils;
import com.azad.moviepedia.models.dtos.WriterDto;
import com.azad.moviepedia.services.WriterService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/writers")
public class WriterRestController implements ApiRestController<WriterDto> {

    @Autowired
    private AppUtils appUtils;

    private final WriterService service;
    private final WriterModelAssembler assembler;

    @Autowired
    public WriterRestController(WriterService service, WriterModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<WriterDto>> createEntity(@Valid @RequestBody WriterDto request) {
        LogUtils.printRequestInfo(WriterRestController.class, "/api/v1/writers", HttpMethod.POST, "USER, ADMIN");

        WriterDto savedDto;
        try {
            LogUtils.logDebug(WriterRestController.class, "Create writer from payload: " + request);
            savedDto = service.create(request);
        } catch (RuntimeException ex) {
            LogUtils.logError(WriterRestController.class, "Error POST /writers: " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(savedDto));
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<WriterDto>>> getAllEntity(@Valid @RequestParam Map<String, String> params) {
        LogUtils.printRequestInfo(WriterRestController.class, "/api/v1/writers", HttpMethod.GET, "USER, ADMIN");

        List<WriterDto> allWritersFromService;
        try {
            LogUtils.logDebug(WriterRestController.class, "Getting all writers with params: " + params);
            allWritersFromService = service.getAll(appUtils.getPagingAndSorting(params));
        } catch (RuntimeException e) {
            LogUtils.logError(WriterRestController.class, "Error GET /writers: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.getCollectionModel(allWritersFromService, params));
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<WriterDto>> getEntity(@Valid @PathVariable("id") Long id) {
        LogUtils.printRequestInfo(WriterRestController.class, "/api/v1/writers/" + id, HttpMethod.GET, "USER, ADMIN");

        WriterDto dtoFromService;
        try {
            LogUtils.logDebug(WriterRestController.class, "Getting writer with id: " + id);
            dtoFromService = service.getById(id);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(WriterRestController.class, "Writer not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            LogUtils.logError(WriterRestController.class, "Error GET /writers/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(dtoFromService));
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<WriterDto>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody WriterDto updatedRequest) {
        LogUtils.printRequestInfo(WriterRestController.class, "/api/v1/writers/" + id, HttpMethod.PUT, "USER, ADMIN");

        WriterDto updatedDto;
        try {
            LogUtils.logDebug(WriterRestController.class, "Updating writer with id: " + id);
            updatedDto = service.updateById(id, updatedRequest);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(WriterRestController.class, "Writer not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            LogUtils.logError(WriterRestController.class, "Error PUT /writers/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(updatedDto));
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        LogUtils.printRequestInfo(WriterRestController.class, "/api/v1/writers/" + id, HttpMethod.DELETE, "USER, ADMIN");

        try {
            LogUtils.logDebug(WriterRestController.class, "Deleting writer with id: " + id);
            service.deleteById(id);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(WriterRestController.class, "Writer not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            LogUtils.logError(WriterRestController.class, "Error DELETE /writers/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Writer deleted with id: " + id);
    }
}
