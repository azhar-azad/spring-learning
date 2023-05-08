package com.azad.ecommerce.productservice.repositories;

import com.azad.ecommerce.productservice.models.entities.ProductLineEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLineRepository extends PagingAndSortingRepository<ProductLineEntity, Long> {

    Optional<ProductLineEntity> findByProductLineUid(Long productLineUid);
}
