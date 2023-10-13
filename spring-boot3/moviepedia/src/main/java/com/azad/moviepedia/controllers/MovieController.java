package com.azad.moviepedia.controllers;

import com.azad.moviepedia.common.ApiController;
import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.movie.MovieDto;
import com.azad.moviepedia.models.movie.MovieRequest;
import com.azad.moviepedia.models.movie.MovieResponse;
import com.azad.moviepedia.services.movie.MovieService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/movies")
public class MovieController implements ApiController<MovieRequest, MovieResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    @Override
    public ResponseEntity<MovieResponse> createEntity(@Valid @RequestBody MovieRequest request) {

        AppUtils.logInfo(MovieController.class, "Handling POST call for entity Movie");

        MovieDto dto = modelMapper.map(request, MovieDto.class);

        MovieDto savedDto;
        try {
            savedDto = service.create(dto);
        } catch (RuntimeException ex) {
            AppUtils.logError(MovieController.class, "Exception occurred in service method. Returning 500");
            return ResponseEntity.internalServerError().build();
        }

        AppUtils.logInfo(MovieController.class, "POST call for entity Movie is handled. Returning ...");
        return new ResponseEntity<>(modelMapper.map(savedDto, MovieResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<MovieResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        AppUtils.logInfo(MovieController.class, "Handling GET ALL call for entity Movie");

        if (page < 0)
            page = 0;

        List<MovieDto> dtosFromService;
        try {
            dtosFromService = service.getAll(new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        } catch (RuntimeException ex) {
            AppUtils.logError(MovieController.class, "Exception occurred in service method. Returning 500");
            return ResponseEntity.internalServerError().build();
        }

        if (dtosFromService == null || dtosFromService.isEmpty()) {
            AppUtils.logInfo(MovieController.class, "Service method returned empty list. Returning 204");
            return ResponseEntity.noContent().build();
        }

        AppUtils.logInfo(MovieController.class, "GET ALL call for entity Movie is handled. Returning ...");
        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, MovieResponse.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<MovieResponse> getEntity(@Valid @PathVariable("id") Long id) {

        AppUtils.logInfo(MovieController.class, "Handling GET call for entity Movie");

        MovieDto dto;
        try {
            dto = service.getById(id);
        } catch (RuntimeException ex) {
            AppUtils.logError(MovieController.class, "Exception occurred in service method. Returning 500");
            return ResponseEntity.internalServerError().build();
        }

        if (dto == null) {
            AppUtils.logInfo(MovieController.class, "Service method returned empty object. Returning 404");
            return ResponseEntity.notFound().build();
        }

        AppUtils.logInfo(MovieController.class, "GET call for entity Movie is handled. Returning ...");
        return new ResponseEntity<>(modelMapper.map(dto, MovieResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<MovieResponse> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody MovieRequest updatedRequest) {

        AppUtils.logInfo(MovieController.class, "Handling PUT call for entity Movie");

        MovieDto dto;
        try {
            dto = service.updateById(id, modelMapper.map(updatedRequest, MovieDto.class));
        } catch (RuntimeException ex) {
            AppUtils.logError(MovieController.class, "Exception occurred in service method. Returning 500");
            return ResponseEntity.internalServerError().build();
        }

        if (dto == null) {
            AppUtils.logInfo(MovieController.class, "Service method returned empty object. Returning 400");
            return ResponseEntity.badRequest().build();
        }

        AppUtils.logInfo(MovieController.class, "PUT call for entity Movie is handled. Returning ...");
        return new ResponseEntity<>(modelMapper.map(dto, MovieResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {

        AppUtils.logInfo(MovieController.class, "Handling DELETE call for entity Movie");

        try {
            service.deleteById(id);
        } catch (RuntimeException ex) {
            AppUtils.logError(MovieController.class, "Exception occurred in service method. Returning 500");
            return ResponseEntity.internalServerError().build();
        }

        AppUtils.logInfo(MovieController.class, "DELETE call for entity Movie is handled. Returning ...");
        return ResponseEntity.ok("Movie Deleted");
    }
}
