package com.azad.supershop.service;

import com.azad.supershop.common.ApiService;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.product.ProductDto;

import java.util.List;

public interface ProductService extends ApiService<ProductDto> {

    List<ProductDto> getAllByCategory(Long categoryId, PagingAndSorting ps);

    ProductDto getByProductName(String productName);
}
