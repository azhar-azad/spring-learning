package com.azad.basicecommerce.api.resources;

import com.azad.basicecommerce.api.assemblers.ProductResponseModelAssembler;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.GenericApiRestController;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.product.ProductDto;
import com.azad.basicecommerce.productservice.models.product.ProductRequest;
import com.azad.basicecommerce.productservice.models.product.ProductResponse;
import com.azad.basicecommerce.productservice.services.product.ProductService;
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
@RequestMapping(path = "/api/v1/productservice/products")
public class ProductRestResource implements GenericApiRestController<ProductRequest, ProductResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    private final ProductService service;
    private final ProductResponseModelAssembler assembler;

    @Autowired
    public ProductRestResource(ProductService service, ProductResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<ProductResponse>> createEntity(@Valid @RequestBody ProductRequest request) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/products", "POST", "ADMIN, SELLER");

        ProductDto dto = modelMapper.map(request, ProductDto.class);

        ProductDto savedDto = service.create(dto);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDto, ProductResponse.class)),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<ProductResponse>>> getAllEntities(int page, int limit, String sort, String order) {
        return null;
    }

    @GetMapping(path = "/byUid/{uid}")
    public ResponseEntity<CollectionModel<EntityModel<ProductResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order,
            @Valid @PathVariable(name = "uid") String uid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/products/byUid/" + uid, "GET", "ADMIN, SELLER, USER");

        if (page < 0) page = 0;

        List<ProductDto> dtosFromService;

        if (uid.toLowerCase().startsWith("store_"))
            dtosFromService = service.getAllByStoreUid(uid,
                    new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
         else if (uid.toLowerCase().startsWith("category_"))
            dtosFromService = service.getAllByCategoryUid(uid,
                    new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
         else
            dtosFromService = null;

        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<ProductResponse> responses = dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, ProductResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductResponse>> responseCollectionModel =
                assembler.getCollectionModel(responses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<ProductResponse>> getEntity(Long id) {
        return null;
    }

    @GetMapping(path = "/{productUid}")
    @Override
    public ResponseEntity<EntityModel<ProductResponse>> getEntity(@Valid @PathVariable("productUid") String productUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/products/" + productUid, "GET", "ADMIN, SELLER, USER");

        ProductDto dtoFromService = service.getByUid(productUid);

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, ProductResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<ProductResponse>> updateEntity(Long id, ProductRequest updatedRequest) {
        return null;
    }

    @PutMapping(path = "/{productUid}")
    @Override
    public ResponseEntity<EntityModel<ProductResponse>> updateEntity(
            @Valid @PathVariable("productUid") String productUid, @RequestBody ProductRequest updatedRequest) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/products/" + productUid, "PUT", "ADMIN, SELLER");

        ProductDto dtoFromService = service.updateByUid(productUid, modelMapper.map(updatedRequest, ProductDto.class));

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, ProductResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }

    @DeleteMapping(path = "/{productUid}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("productUid") String productUid) {

        apiUtils.printResourceRequestInfo("/api/v1/productservice/products/" + productUid, "DELETE", "ADMIN, SELLER");

        service.deleteByUid(productUid);

        return ResponseEntity.ok().build();
    }
}
