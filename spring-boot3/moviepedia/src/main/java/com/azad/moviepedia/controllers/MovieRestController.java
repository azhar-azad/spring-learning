package com.azad.moviepedia.controllers;

import com.azad.moviepedia.assemblers.MovieModelAssembler;
import com.azad.moviepedia.common.ApiRestController;
import com.azad.moviepedia.common.LogUtils;
import com.azad.moviepedia.models.dtos.MovieDto;
import com.azad.moviepedia.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/movies")
public class MovieRestController implements ApiRestController<MovieDto> {

    private final MovieService service;
    private final MovieModelAssembler assembler;

    @Autowired
    public MovieRestController(MovieService service, MovieModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<MovieDto>> createEntity(@Valid @RequestBody MovieDto request) {
        LogUtils.printRequestInfo(MovieRestController.class, "/api/v1/movies", HttpMethod.POST, "USER, ADMIN");
        MovieDto savedDto;
        try {
            LogUtils.logDebug(MovieRestController.class, "Create movie from payload: " + request);
            savedDto = service.create(request);
        } catch (RuntimeException e) {
            LogUtils.logError(MovieRestController.class, "Error POST /movies: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(assembler.toModel(savedDto));
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<MovieDto>>> getAllEntity(@Valid @RequestParam Map<String, String> params) {
        return null;
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<MovieDto>> getEntity(@Valid @PathVariable("id") Long id) {
        return null;
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<MovieDto>> updateEntity(@PathVariable("id") Long id, @RequestBody MovieDto updatedRequest) {
        return null;
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {
        return null;
    }
}
