package com.azad.moneymanagerapi.repositories;

import com.azad.moneymanagerapi.models.entities.SubcategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends PagingAndSortingRepository<SubcategoryEntity, Long> {

    Optional<List<SubcategoryEntity>> findByCategoryId(Long categoryId, Pageable pageable);
}
