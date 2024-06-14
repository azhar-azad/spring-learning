package com.azad.online_shop.controller;

import com.azad.online_shop.common.Utils;
import com.azad.online_shop.model.dto.products.ProductDto;
import com.azad.online_shop.model.dto.products.ProductRequest;
import com.azad.online_shop.model.dto.products.ProductResponse;
import com.azad.online_shop.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private ModelMapper modelMapper;

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest req) {
        Utils.logInfo(ProductController.class, "POST /api/v1/products");
        ProductDto productDto = modelMapper.map(req, ProductDto.class);

        ProductDto savedProduct;
        try {
            savedProduct = productService.create(productDto);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(modelMapper.map(savedProduct, ProductResponse.class));
    }
}
