package com.azad.basicecommerce.api.resources;

import com.azad.basicecommerce.api.assemblers.CategoryResponseModelAssembler;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.GenericApiRestController;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.category.CategoryDto;
import com.azad.basicecommerce.productservice.models.category.CategoryRequest;
import com.azad.basicecommerce.productservice.models.category.CategoryResponse;
import com.azad.basicecommerce.productservice.services.category.CategoryService;
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
@RequestMapping(path = "/api/v1/productservice/categories")
public class CategoryRestResource implements GenericApiRestController<CategoryRequest, CategoryResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    private final CategoryService service;
    private final CategoryResponseModelAssembler assembler;

    @Autowired
    public CategoryRestResource(CategoryService service, CategoryResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> createEntity(@Valid @RequestBody CategoryRequest request) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/categories", "POST", "ADMIN");

        CategoryDto dto = modelMapper.map(request, CategoryDto.class);

        CategoryDto savedDto = service.create(dto);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDto, CategoryResponse.class)), HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<CategoryResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/categories", "GET", "ADMIN");

        if (page < 0) page = 0;

        List<CategoryDto> dtosFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<CategoryResponse> responses = dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, CategoryResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CategoryResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> getEntity(Long id) {
        return null;
    }

    @GetMapping(path = "/{categoryUid}")
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> getEntity(@Valid @PathVariable("categoryUid") String categoryUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/categories/" + categoryUid, "GET", "ADMIN");

        CategoryDto dtoFromService = service.getByUid(categoryUid);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, CategoryResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> updateEntity(Long id, CategoryRequest updatedRequest) {
        return null;
    }

    @PutMapping(path = "/{categoryUid}")
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> updateEntity(
            @Valid @PathVariable("categoryUid") String categoryUid, @RequestBody CategoryRequest updatedRequest) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/categories/" + categoryUid, "PUT", "ADMIN");

        CategoryDto dtoFromService = service.updateByUid(categoryUid, modelMapper.map(updatedRequest, CategoryDto.class));

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, CategoryResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }

    @DeleteMapping(path = "/{categoryUid}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("categoryUid") String categoryUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/categories/" + categoryUid, "DELETE", "ADMIN");

        service.deleteByUid(categoryUid);

        return ResponseEntity.ok().build();
    }
}
