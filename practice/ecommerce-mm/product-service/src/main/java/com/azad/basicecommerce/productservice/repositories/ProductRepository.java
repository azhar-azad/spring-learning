package com.azad.basicecommerce.productservice.repositories;

import com.azad.basicecommerce.productservice.models.product.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByProductUid(String productUid);
    Optional<List<ProductEntity>> findByCategoryId(Long categoryId);
}
