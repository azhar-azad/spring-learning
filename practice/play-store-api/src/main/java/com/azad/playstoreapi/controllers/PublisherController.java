package com.azad.playstoreapi.controllers;

import com.azad.playstoreapi.models.dtos.PublisherDto;
import com.azad.playstoreapi.models.pojos.Publisher;
import com.azad.playstoreapi.models.responses.PublisherResponse;
import com.azad.playstoreapi.services.PublisherService;
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
@RequestMapping(path = "/api/v1/publishers")
public class PublisherController {

    @Autowired
    private ModelMapper modelMapper;

    private final PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PublisherResponse> createPublisher(@Valid @RequestBody Publisher publisher) {

        PublisherDto dto = modelMapper.map(publisher, PublisherDto.class);
        PublisherDto savedDto = service.create(dto);

        if (savedDto == null) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(savedDto, PublisherResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponse>> getAllPublishers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<PublisherDto> allPublishersFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allPublishersFromService == null || allPublishersFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<PublisherResponse> publisherResponses = allPublishersFromService.stream()
                .map(publisherDto -> modelMapper.map(publisherDto, PublisherResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(publisherResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PublisherResponse> getPublisher(@Valid @PathVariable("id") Long id) {

        PublisherDto publisherFromService = service.getById(id);
        if (publisherFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(modelMapper.map(publisherFromService, PublisherResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PublisherResponse> updatePublisher(@Valid @PathVariable("id") Long id, @RequestBody Publisher updatedPublisher) {

        PublisherDto publisherFromService = service.updateById(id, modelMapper.map(updatedPublisher, PublisherDto.class));

        return new ResponseEntity<>(modelMapper.map(publisherFromService, PublisherResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePublisher(@Valid @PathVariable("id") Long id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
