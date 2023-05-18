package com.azad.basicecommerce.productservice.services.product;

import com.azad.basicecommerce.common.GenericApiService;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.product.ProductDto;

import java.util.List;

public interface ProductService extends GenericApiService<ProductDto> {

    List<ProductDto> getAllByStoreUid(String storeUid, PagingAndSorting ps);
    List<ProductDto> getAllByCategoryUid(String categoryUid, PagingAndSorting ps);
}
