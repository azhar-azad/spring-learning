package com.azad.basicecommerce.api.resources;

import com.azad.basicecommerce.api.assemblers.RatingResponseModelAssembler;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.GenericApiRestController;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.rating.RatingDto;
import com.azad.basicecommerce.productservice.models.rating.RatingRequest;
import com.azad.basicecommerce.productservice.models.rating.RatingResponse;
import com.azad.basicecommerce.productservice.services.rating.RatingService;
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
@RequestMapping(path = "/api/v1/productservice/ratings")
public class RatingRestResource implements GenericApiRestController<RatingRequest, RatingResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    private final RatingService service;
    private final RatingResponseModelAssembler assembler;

    @Autowired
    public RatingRestResource(RatingService service, RatingResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<RatingResponse>> createEntity(@Valid @RequestBody RatingRequest request) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/ratings", "POST", "USER, ADMIN");

        RatingDto dto = modelMapper.map(request, RatingDto.class);

        RatingDto savedDto = service.create(dto);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDto, RatingResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<RatingResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/ratings", "GET", "USER, ADMIN");

        if (page < 0) page = 0;

        List<RatingDto> dtosFromService = service.getAll
                (new PagingAndSorting(page > 1 ? page - 1 : page, limit, sort, order));
        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(assembler.getCollectionModel(dtosFromService.stream()
                        .map(dto -> modelMapper.map(dto, RatingResponse.class))
                        .collect(Collectors.toList()),
                new PagingAndSorting(page, limit, sort, order)),
                HttpStatus.OK);
    }

    @GetMapping(path = "/byProduct/{productUid}")
    public ResponseEntity<CollectionModel<EntityModel<RatingResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order,
            @Valid @PathVariable("productUid") String productUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/ratings/byProduct" + productUid, "GET", "USER, ADMIN");

        if (page < 0) page = 0;

        List<RatingDto> dtosFromService = service.getAllByProduct(productUid,
                new PagingAndSorting(page > 1 ? page - 1 : page, limit, sort, order));
        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(assembler.getCollectionModel(dtosFromService.stream()
                        .map(dto -> modelMapper.map(dto, RatingResponse.class))
                        .collect(Collectors.toList()),
                new PagingAndSorting(page, limit, sort, order)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<RatingResponse>> getEntity(Long id) {
        return null;
    }

    @GetMapping(path = "/{ratingUid}")
    @Override
    public ResponseEntity<EntityModel<RatingResponse>> getEntity(@Valid @PathVariable("ratingUid") String ratingUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/ratings/" + ratingUid, "GET", "USER, ADMIN");

        RatingDto dtoFromService = service.getByUid(ratingUid);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, RatingResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<RatingResponse>> updateEntity(Long id, RatingRequest updatedRequest) {
        return null;
    }

    @PutMapping(path = "/{ratingUid}")
    @Override
    public ResponseEntity<EntityModel<RatingResponse>> updateEntity(
            @Valid @PathVariable("ratingUid") String ratingUid, @RequestBody RatingRequest updatedRequest) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/ratings/" + ratingUid, "PUT", "USER, ADMIN");

        RatingDto dtoFromService = service.updateByUid(ratingUid, modelMapper.map(updatedRequest, RatingDto.class));

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, RatingResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }

    @DeleteMapping(path = "/{ratingUid}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("ratingUid") String ratingUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/ratings/" + ratingUid, "DELETE", "USER, ADMIN");

        service.deleteByUid(ratingUid);

        return ResponseEntity.ok("Rating Deleted");
    }
}
