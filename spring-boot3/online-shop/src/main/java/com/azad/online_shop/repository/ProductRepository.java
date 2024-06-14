package com.azad.online_shop.repository;

import com.azad.online_shop.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByProductName(String productName);
    Optional<ProductEntity> findByProductUniqueName(String productUniqueName);
}
