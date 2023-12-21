package com.azad.supershop.repository;

import com.azad.supershop.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<List<ProductEntity>> findByCategoryId(Long categoryId);
}
