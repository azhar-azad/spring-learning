package com.azad.onlinecourse.repository;

import com.azad.onlinecourse.models.subcategory.SubcategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {

    Optional<List<SubcategoryEntity>> findByCategory(Long categoryId, Pageable pageable);
}
