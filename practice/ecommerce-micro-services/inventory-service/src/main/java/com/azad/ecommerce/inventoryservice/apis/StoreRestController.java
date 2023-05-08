package com.azad.ecommerce.inventoryservice.apis;

import com.azad.ecommerce.inventoryservice.assemblers.StoreResponseModelAssembler;
import com.azad.ecommerce.inventoryservice.models.dtos.StoreDto;
import com.azad.ecommerce.inventoryservice.models.requests.StoreRequest;
import com.azad.ecommerce.inventoryservice.models.responses.StoreResponse;
import com.azad.ecommerce.inventoryservice.services.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/inventoryservice/stores")
public class StoreRestController implements GenericApiRestController<StoreRequest, StoreResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final StoreService service;
    private final StoreResponseModelAssembler assembler;

    @Autowired
    public StoreRestController(StoreService service, StoreResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<StoreResponse>> createEntity(@Valid @RequestBody StoreRequest request) {

        StoreDto dtoFromReq = modelMapper.map(request, StoreDto.class);

        StoreDto dtoFromService = service.create(dtoFromReq);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, StoreResponse.class)),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<StoreResponse>>> getAllEntities(int page, int limit, String sort, String order) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<StoreResponse>> getEntity(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<StoreResponse>> updateEntity(Long id, StoreRequest updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }
}
