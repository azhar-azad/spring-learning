package com.azad.ecommerce.productservice.repositories;

import com.azad.ecommerce.productservice.models.entities.CategoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByCategoryUid(Long categoryUid);
    Optional<List<CategoryEntity>> findByProductLineUid(Long productLineUid);
}
