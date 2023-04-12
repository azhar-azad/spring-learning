package com.azad.web.controllers;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.dtos.TagDto;
import com.azad.data.models.requests.TagRequest;
import com.azad.data.models.responses.TagResponse;
import com.azad.service.tag.TagService;
import com.azad.web.assemblers.TagModelAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/tags")
public class TagRestController implements GenericApiRestController<TagRequest, TagResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final TagService service;
    private final TagModelAssembler assembler;

    @Autowired
    public TagRestController(TagService service, TagModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<TagResponse>> createEntity(@Valid @RequestBody TagRequest request) {

        TagDto dto = modelMapper.map(request, TagDto.class);

        TagDto savedDto = service.create(dto);

        TagResponse response = modelMapper.map(savedDto, TagResponse.class);
        EntityModel<TagResponse> responseEntityModel = assembler.toModel(response);

        if (response.getUnregisteredUserMsg() != null)
            return new ResponseEntity<>(responseEntityModel, HttpStatus.UNAUTHORIZED);
        if (response.getMessage() != null)
            return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<TagResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<TagDto> allTagsFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allTagsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<TagResponse> responses = allTagsFromService.stream()
                .map(dto -> modelMapper.map(dto, TagResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TagResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TagResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        TagDto tagFromService = service.getById(id);
        if (tagFromService == null)
            return ResponseEntity.notFound().build();

        TagResponse response = modelMapper.map(tagFromService, TagResponse.class);
        EntityModel<TagResponse> responseEntityModel = assembler.toModel(response);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TagResponse>> updateEntity(
            @Valid @PathVariable("id") Long id, @RequestBody TagRequest updatedRequest) {

        TagDto tagFromService = service.updateById(id, modelMapper.map(updatedRequest, TagDto.class));

        TagResponse response = modelMapper.map(tagFromService, TagResponse.class);
        EntityModel<TagResponse> responseEntityModel = assembler.toModel(response);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
