package com.azad.basicecommerce.api.resources;

import com.azad.basicecommerce.api.assemblers.ReviewResponseModelAssembler;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.GenericApiRestController;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.review.ReviewDto;
import com.azad.basicecommerce.productservice.models.review.ReviewRequest;
import com.azad.basicecommerce.productservice.models.review.ReviewResponse;
import com.azad.basicecommerce.productservice.services.review.ReviewService;
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
@RequestMapping(path = "/api/v1/productservice/reviews")
public class ReviewRestResource implements GenericApiRestController<ReviewRequest, ReviewResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    private final ReviewService service;
    private final ReviewResponseModelAssembler assembler;

    @Autowired
    public ReviewRestResource(ReviewService service, ReviewResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<ReviewResponse>> createEntity(@Valid @RequestBody ReviewRequest request) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/reviews", "POST", "USER, ADMIN");

        ReviewDto dto = modelMapper.map(request, ReviewDto.class);

        ReviewDto savedDto = service.create(dto);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDto, ReviewResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<ReviewResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/reviews", "GET", "USER, ADMIN");

        if (page < 0) page = 0;

        List<ReviewDto> dtosFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(assembler.getCollectionModel(dtosFromService.stream()
                        .map(dto -> modelMapper.map(dto, ReviewResponse.class))
                        .collect(Collectors.toList()),
                new PagingAndSorting(page, limit, sort, order)),
                HttpStatus.OK);
    }

    @GetMapping(path = "/byProduct/{productUid}")
    public ResponseEntity<CollectionModel<EntityModel<ReviewResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order,
            @Valid @PathVariable("productUid") String productUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/reviews/byProduct/" + productUid, "GET", "USER, ADMIN");

        if (page < 0) page = 0;

        List<ReviewDto> dtosFromService = service.getAllByProduct(productUid,
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(assembler.getCollectionModel(dtosFromService.stream()
                        .map(dto -> modelMapper.map(dto, ReviewResponse.class))
                        .collect(Collectors.toList()),
                new PagingAndSorting(page, limit, sort, order)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<ReviewResponse>> getEntity(Long id) {
        return null;
    }

    @GetMapping(path = "/{reviewUid}")
    @Override
    public ResponseEntity<EntityModel<ReviewResponse>> getEntity(@Valid @PathVariable("reviewUid") String reviewUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/reviews/" + reviewUid, "GET", "USER, ADMIN");

        ReviewDto dto = service.getByUid(reviewUid);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dto, ReviewResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<ReviewResponse>> updateEntity(Long id, ReviewRequest updatedRequest) {
        return null;
    }

    @PutMapping(path = "/{reviewUid}")
    @Override
    public ResponseEntity<EntityModel<ReviewResponse>> updateEntity(
            @Valid @PathVariable("reviewUid") String reviewUid, @RequestBody ReviewRequest updatedRequest) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/reviews/" + reviewUid, "PUT", "USER, ADMIN");

        ReviewDto updatedDto = service.updateByUid(reviewUid, modelMapper.map(updatedRequest, ReviewDto.class));

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(updatedDto, ReviewResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }

    @DeleteMapping(path = "/{reviewUid}")
    @Override
    public ResponseEntity<?> deleteEntity(String reviewUid) {
        apiUtils.printResourceRequestInfo("/api/v1/productservice/reviews/" + reviewUid, "PUT", "USER, ADMIN");

        service.deleteByUid(reviewUid);

        return ResponseEntity.ok("Review Deleted");
    }
}
