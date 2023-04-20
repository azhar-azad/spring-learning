package com.azad.moneymanagerapi.repositories;

import com.azad.moneymanagerapi.models.entities.CategoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
}
