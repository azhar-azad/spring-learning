package com.azad.basicecommerce.productservice.repositories;

import com.azad.basicecommerce.productservice.models.category.CategoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByCategoryUid(String categoryUid);
    Optional<List<CategoryEntity>> findByProductLineId(Long productLineId);
}
