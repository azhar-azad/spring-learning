package com.azad.moviepedia.controllers;

import com.azad.moviepedia.assemblers.DirectorModelAssembler;
import com.azad.moviepedia.common.ApiRestController;
import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.LogUtils;
import com.azad.moviepedia.models.dtos.DirectorDto;
import com.azad.moviepedia.services.DirectorService;
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
@RequestMapping(path = "/api/v1/directors")
public class DirectorRestController implements ApiRestController<DirectorDto> {

    @Autowired
    private AppUtils appUtils;

    private final DirectorService service;
    private final DirectorModelAssembler assembler;

    @Autowired
    public DirectorRestController(DirectorService service, DirectorModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<DirectorDto>> createEntity(@Valid @RequestBody DirectorDto request) {
        LogUtils.printRequestInfo(DirectorRestController.class, "/api/v1/directors", HttpMethod.POST, "USER, ADMIN");

        DirectorDto savedDto;
        try {
            LogUtils.logDebug(DirectorRestController.class, "Create director from payload: " + request);
            savedDto = service.create(request);
        } catch (RuntimeException ex) {
            LogUtils.logError(DirectorRestController.class, "Error POST /directors: " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(savedDto));
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<DirectorDto>>> getAllEntity(@Valid @RequestParam Map<String, String> params) {
        LogUtils.printRequestInfo(DirectorRestController.class, "/api/v1/directors", HttpMethod.GET, "USER, ADMIN");

        List<DirectorDto> allDirectorsFromService;
        try {
            LogUtils.logDebug(DirectorRestController.class, "Getting all directors with params: " + params);
            allDirectorsFromService = service.getAll(appUtils.getPagingAndSorting(params));
        } catch (RuntimeException e) {
            LogUtils.logError(DirectorRestController.class, "Error GET /directors: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.getCollectionModel(allDirectorsFromService, params));
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<DirectorDto>> getEntity(@Valid @PathVariable("id") Long id) {
        LogUtils.printRequestInfo(DirectorRestController.class, "/api/v1/directors/" + id, HttpMethod.GET, "USER, ADMIN");

        DirectorDto dtoFromService;
        try {
            LogUtils.logDebug(DirectorRestController.class, "Getting director with id: " + id);
            dtoFromService = service.getById(id);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(DirectorRestController.class, "Director not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            LogUtils.logError(DirectorRestController.class, "Error GET /directors/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(dtoFromService));
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<DirectorDto>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody DirectorDto updatedRequest) {
        LogUtils.printRequestInfo(DirectorRestController.class, "/api/v1/directors/" + id, HttpMethod.PUT, "USER, ADMIN");

        DirectorDto updatedDto;
        try {
            LogUtils.logDebug(DirectorRestController.class, "Updating director with id: " + id);
            updatedDto = service.updateById(id, updatedRequest);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(DirectorRestController.class, "Director not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            LogUtils.logError(DirectorRestController.class, "Error PUT / directors/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(assembler.toModel(updatedDto));
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        LogUtils.printRequestInfo(DirectorRestController.class, "/api/v1/directors/" + id, HttpMethod.DELETE, "USER, ADMIN");

        try {
            LogUtils.logDebug(DirectorRestController.class, "Deleting director with id: " + id);
            service.deleteById(id);
        } catch (EntityNotFoundException e) {
            LogUtils.logError(DirectorRestController.class, "Director not found with id: " + id + ":" + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            LogUtils.logError(DirectorRestController.class, "Error DELETE / directors/" + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Director deleted with id: " + id);
    }
}
