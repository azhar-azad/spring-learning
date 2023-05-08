package com.azad.ecommerce.productservice.repositories;

import com.azad.ecommerce.productservice.models.entities.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByProductUid(Long productUid);
}
