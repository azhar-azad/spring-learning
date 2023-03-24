package com.azad.playstoreapi.controllers;

import com.azad.playstoreapi.models.dtos.AppDto;
import com.azad.playstoreapi.models.dtos.PublisherDto;
import com.azad.playstoreapi.models.pojos.App;
import com.azad.playstoreapi.models.pojos.Publisher;
import com.azad.playstoreapi.models.requests.AppRequest;
import com.azad.playstoreapi.models.responses.AppResponse;
import com.azad.playstoreapi.models.responses.PublisherResponse;
import com.azad.playstoreapi.services.AppService;
import com.azad.playstoreapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/apps")
public class AppController {

    @Autowired
    private ModelMapper modelMapper;

    private final AppService service;

    @Autowired
    public AppController(AppService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppResponse> createApp(@Valid @RequestBody AppRequest app) {

        AppDto appFromService = service.create(modelMapper.map(app, AppDto.class));

        if (appFromService == null) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(appFromService, AppResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppResponse>> getAllApps(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<AppDto> allAppsFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allAppsFromService == null || allAppsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<AppResponse> appResponses = allAppsFromService.stream()
                .map(appDto -> modelMapper.map(appDto, AppResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(appResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<List<AppResponse>> getAllAppsByPublisher(
            @Valid @PathVariable("publisherId") Long publisherId,
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<AppDto> allAppsFromService = service.getAllByPublisher(
                publisherId, new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));

        if (allAppsFromService == null || allAppsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<AppResponse> appResponses = allAppsFromService.stream()
                .map(appDto -> modelMapper.map(appDto, AppResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(appResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AppResponse> getApp(@Valid @PathVariable("id") Long id) {

        AppDto appFromService = service.getById(id);
        if (appFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(modelMapper.map(appFromService, AppResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AppResponse> updateApp(@Valid @PathVariable("id") Long id, @RequestBody AppRequest updatedApp) {

        AppDto appFromService = service.updateById(id, modelMapper.map(updatedApp, AppDto.class));

        return new ResponseEntity<>(modelMapper.map(appFromService, AppResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteApp(@Valid @PathVariable("id") Long id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
