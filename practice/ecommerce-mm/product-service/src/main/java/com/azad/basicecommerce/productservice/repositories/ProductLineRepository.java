package com.azad.basicecommerce.productservice.repositories;

import com.azad.basicecommerce.productservice.models.productline.ProductLineEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLineRepository extends PagingAndSortingRepository<ProductLineEntity, Long> {

    Optional<ProductLineEntity> findByProductLineUid(String productLineUid);
}
