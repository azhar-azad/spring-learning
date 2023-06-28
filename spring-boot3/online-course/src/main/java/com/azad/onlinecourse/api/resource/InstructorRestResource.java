package com.azad.onlinecourse.api.resource;

import com.azad.onlinecourse.api.assembler.InstructorResponseModelAssembler;
import com.azad.onlinecourse.common.GenericApiRestController;
import com.azad.onlinecourse.models.instructor.InstructorDto;
import com.azad.onlinecourse.models.instructor.InstructorRequest;
import com.azad.onlinecourse.models.instructor.InstructorResponse;
import com.azad.onlinecourse.service.InstructorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/instructors")
public class InstructorRestResource implements GenericApiRestController<InstructorRequest, InstructorResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final InstructorService service;
    private final InstructorResponseModelAssembler assembler;

    @Autowired
    public InstructorRestResource(InstructorService service, InstructorResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<InstructorResponse>> createEntity(@RequestBody InstructorRequest request) {

        InstructorDto dto = modelMapper.map(request, InstructorDto.class);

        InstructorDto savedDto = service.create(dto);

        InstructorResponse response = modelMapper.map(savedDto, InstructorResponse.class);

        return new ResponseEntity<>(assembler.toModel(response),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<InstructorResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        return null;
    }

    @Override
    public ResponseEntity<EntityModel<InstructorResponse>> getEntity(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<InstructorResponse>> updateEntity(Long id, InstructorRequest updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }
}
