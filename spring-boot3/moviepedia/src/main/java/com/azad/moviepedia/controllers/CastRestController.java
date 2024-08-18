package com.azad.moviepedia.controllers;

import com.azad.moviepedia.assemblers.CastModelAssembler;
import com.azad.moviepedia.common.ApiRestController;
import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.LogUtils;
import com.azad.moviepedia.models.dtos.CastDto;
import com.azad.moviepedia.services.CastService;
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
@RequestMapping(path = "/api/v1/casts")
public class CastRestController implements ApiRestController<CastDto> {

    @Autowired
    private AppUtils appUtils;

    private final CastService service;
    private final CastModelAssembler assembler;

    @Autowired
    public CastRestController(CastService service, CastModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<CastDto>> createEntity(@Valid @RequestBody CastDto request) {
        LogUtils.printRequestInfo(CastRestController.class, "/api/v1/casts", HttpMethod.POST, "USER, ADMIN");

        CastDto savedDto;
        try {
            LogUtils.logDebug(CastRestController.class, "Create cast from payload: " + request);
            savedDto = service.create(request);
        } catch (RuntimeException ex) {
            LogUtils.logError(CastRestController.class, "Error POST /casts: " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(savedDto));
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<CastDto>>> getAllEntity(@Valid @RequestParam Map<String, String> params) {
        LogUtils.printRequestInfo(CastRestController.class, "/api/v1/casts", HttpMethod.GET, "USER, ADMIN");

        List<CastDto> allCastsFromService;
        try {
            LogUtils.logDebug(CastRestController.class, "Getting all casts with params: " + params);
            allCastsFromService = service.getAll(appUtils.getPagingAndSorting(params));
        } catch (RuntimeException e) {
            LogUtils.logError(CastRestController.class, "Error GET /casts: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.getCollectionModel(allCastsFromService, params));
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<CastDto>> getEntity(@Valid @PathVariable("id") Long id) {
        LogUtils.printRequestInfo(CastRestController.class, "/api/v1/casts/" + id, HttpMethod.GET, "USER, ADMIN");

        CastDto dtoFromService;
        try {
            LogUtils.logDebug(CastRestController.class, "Getting cast with id: " + id);
            dtoFromService = service.getById(id);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(CastRestController.class, "Cast not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            LogUtils.logError(CastRestController.class, "Error GET /casts/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(dtoFromService));
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<CastDto>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody CastDto updatedRequest) {
        LogUtils.printRequestInfo(CastRestController.class, "/api/v1/casts/" + id, HttpMethod.PUT, "USER, ADMIN");

        CastDto updatedDto;
        try {
            LogUtils.logDebug(CastRestController.class, "Updating cast with id: " + id);
            updatedDto = service.updateById(id, updatedRequest);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(CastRestController.class, "Cast not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            LogUtils.logError(CastRestController.class, "Error PUT /casts/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(updatedRequest));
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        LogUtils.printRequestInfo(CastRestController.class, "/api/v1/casts/" + id, HttpMethod.DELETE, "USER, ADMIN");

        try {
            LogUtils.logDebug(CastRestController.class, "Deleting cast with id: " + id);
            service.deleteById(id);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(CastRestController.class, "Cast not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            LogUtils.logError(CastRestController.class, "Error DELETE /casts/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Cast deleted with id: " + id);
    }
}
