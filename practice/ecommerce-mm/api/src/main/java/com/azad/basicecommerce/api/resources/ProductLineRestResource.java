package com.azad.basicecommerce.api.resources;

import com.azad.basicecommerce.api.assemblers.ProductLineResponseModelAssembler;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.GenericApiRestController;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.productline.ProductLineDto;
import com.azad.basicecommerce.productservice.models.productline.ProductLineRequest;
import com.azad.basicecommerce.productservice.models.productline.ProductLineResponse;
import com.azad.basicecommerce.productservice.services.productline.ProductLineService;
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
@RequestMapping(path = "/api/v1/productservice/productLines")
public class ProductLineRestResource implements GenericApiRestController<ProductLineRequest, ProductLineResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    private final ProductLineService service;
    private final ProductLineResponseModelAssembler assembler;

    @Autowired
    public ProductLineRestResource(ProductLineService service, ProductLineResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<ProductLineResponse>> createEntity(@Valid @RequestBody ProductLineRequest request) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/productLines", "POST", "ADMIN");

        ProductLineDto dto = modelMapper.map(request, ProductLineDto.class);

        ProductLineDto savedDto = service.create(dto);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDto, ProductLineResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<ProductLineResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/productLines", "GET", "ADMIN");

        if (page < 0) page = 0;

        List<ProductLineDto> dtosFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<ProductLineResponse> responses = dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, ProductLineResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductLineResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses , new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<ProductLineResponse>> getEntity(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<ProductLineResponse>> getEntity(String uid) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<ProductLineResponse>> updateEntity(Long id, ProductLineRequest updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<ProductLineResponse>> updateEntity(String uid, ProductLineRequest updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(String uid) {
        return null;
    }
}
