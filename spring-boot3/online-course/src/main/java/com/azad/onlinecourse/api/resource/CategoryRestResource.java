package com.azad.onlinecourse.api.resource;

import com.azad.onlinecourse.api.assembler.CategoryResponseModelAssembler;
import com.azad.onlinecourse.common.exception.ResourceNotFoundException;
import com.azad.onlinecourse.common.exception.UnauthorizedAccessException;
import com.azad.onlinecourse.common.generics.GenericApiRestController;
import com.azad.onlinecourse.common.PagingAndSorting;
import com.azad.onlinecourse.common.util.ApiUtils;
import com.azad.onlinecourse.models.category.CategoryDto;
import com.azad.onlinecourse.models.category.CategoryRequest;
import com.azad.onlinecourse.models.category.CategoryResponse;
import com.azad.onlinecourse.models.subcategory.SubcategoryDto;
import com.azad.onlinecourse.models.subcategory.SubcategoryRequest;
import com.azad.onlinecourse.models.subcategory.SubcategoryResponse;
import com.azad.onlinecourse.service.CategoryService;
import com.azad.onlinecourse.service.SubcategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryRestResource implements GenericApiRestController<CategoryRequest, CategoryResponse> {

    private final String BASE_URL = "/api/v1/categories";
    private final CategoryResponse globalCategoryResponse = new CategoryResponse();
    private final SubcategoryResponse globalSubcategoryResponse = new SubcategoryResponse();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private SubcategoryService subcategoryService;

    private final CategoryService categoryService;
    private final CategoryResponseModelAssembler assembler;

    @Autowired
    public CategoryRestResource(CategoryService categoryService, CategoryResponseModelAssembler assembler) {
        this.categoryService = categoryService;
        this.assembler = assembler;
    }

    @PostMapping(path = "/{categoryId}/subcategories")
    public ResponseEntity<EntityModel<SubcategoryResponse>> createSubcategory(
            @Valid @PathVariable("categoryId") Long categoryId, @Valid @RequestBody SubcategoryRequest request) {

        apiUtils.printRequestInfo(CategoryRestResource.class, BASE_URL + "/" + categoryId + "/subcategories", "GET", "ADMIN");

        SubcategoryDto dtoFromService;
        try {
            dtoFromService = subcategoryService.create(modelMapper.map(request, SubcategoryDto.class));
        } catch (UnauthorizedAccessException ex) {
            globalSubcategoryResponse.setError(apiUtils.getApiErrorForUnauthorizedAccess(ex));
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(assembler.toModel(globalSubcategoryResponse));
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        apiUtils.logInfo(CategoryRestResource.class, "CATEGORY :: CREATE: SUCCESS");

//        return new ResponseEntity<>(
//                assembler.toModel(modelMapper.map(dtoFromService, CategoryResponse.class)),
//                HttpStatus.CREATED);

        return null;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> createEntity(@Valid @RequestBody CategoryRequest request) {

        apiUtils.printRequestInfo(CategoryRestResource.class, BASE_URL, "POST", "ADMIN");

        CategoryDto dtoFromService;
        try {
            dtoFromService = categoryService.create(modelMapper.map(request, CategoryDto.class));
        } catch (UnauthorizedAccessException ex) {
            return handleResponseForUnauthorizedAccess(ex);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        apiUtils.logInfo(CategoryRestResource.class, "CATEGORY :: CREATE: SUCCESS");

        return new ResponseEntity<>(
                assembler.toModel(modelMapper.map(dtoFromService, CategoryResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<CategoryResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        apiUtils.printRequestInfo(CategoryRestResource.class, BASE_URL, "GET", "ADMIN, STUDENT, INSTRUCTOR, USER");

        if (page < 0) page = 0;

        List<CategoryDto> dtosFromService;
        try {
            dtosFromService = categoryService.getAll(
                    new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        apiUtils.logInfo(CategoryRestResource.class, "CATEGORY :: GET ALL: SUCCESS");

        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(
                assembler.getCollectionModel(
                        dtosFromService.stream()
                                .map(dto -> modelMapper.map(dto, CategoryResponse.class))
                                .toList(),
                        new PagingAndSorting(page, limit, sort, order)),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        apiUtils.printRequestInfo(CategoryRestResource.class, BASE_URL + "/" + id, "GET", "ADMIN");

        CategoryDto dtoFromService;
        try {
            dtoFromService = categoryService.getById(id);
        } catch (UnauthorizedAccessException ex) {
            return handleResponseForUnauthorizedAccess(ex);
        } catch (ResourceNotFoundException ex) {
            return handleResponseForResourceNotFound(ex);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        apiUtils.logInfo(CategoryRestResource.class, "CATEGORY :: GET BY ID: SUCCESS");

        if (dtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(
                assembler.toModel(modelMapper.map(dtoFromService, CategoryResponse.class)),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<CategoryResponse>> updateEntity(
            @Valid @PathVariable("id") Long id,
            @RequestBody CategoryRequest updatedRequest) {

        apiUtils.printRequestInfo(CategoryRestResource.class, BASE_URL + "/" + id, "PUT", "ADMIN");

        CategoryDto dtoFromService;
        try {
            dtoFromService = categoryService.updateById(id, modelMapper.map(updatedRequest, CategoryDto.class));
        } catch (UnauthorizedAccessException ex) {
            return handleResponseForUnauthorizedAccess(ex);
        } catch (ResourceNotFoundException ex) {
            return handleResponseForResourceNotFound(ex);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        apiUtils.logInfo(CategoryRestResource.class, "CATEGORY :: UPDATE BY ID: SUCCESS");

        if (dtoFromService == null)
            return ResponseEntity.badRequest().build();

        return new ResponseEntity<>(
                assembler.toModel(modelMapper.map(dtoFromService, CategoryResponse.class)),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {

        apiUtils.printRequestInfo(CategoryRestResource.class, BASE_URL + "/" + id, "DELETE", "ADMIN");

        try {
            categoryService.deleteById(id);
        } catch (UnauthorizedAccessException ex) {
            return handleResponseForUnauthorizedAccess(ex);
        } catch (ResourceNotFoundException ex) {
            return handleResponseForResourceNotFound(ex);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
        apiUtils.logInfo(CategoryRestResource.class, "CATEGORY :: DELETE BY ID: SUCCESS");

        return ResponseEntity.ok("Category Deleted");
    }

    private ResponseEntity<EntityModel<CategoryResponse>> handleResponseForUnauthorizedAccess(UnauthorizedAccessException ex) {
        globalCategoryResponse.setError(apiUtils.getApiErrorForUnauthorizedAccess(ex));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(assembler.toModel(globalCategoryResponse));
    }

    private ResponseEntity<EntityModel<CategoryResponse>> handleResponseForResourceNotFound(ResourceNotFoundException ex) {
        globalCategoryResponse.setError(apiUtils.getApiErrorForResourceNotFound(ex));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(assembler.toModel(globalCategoryResponse));
    }
}
