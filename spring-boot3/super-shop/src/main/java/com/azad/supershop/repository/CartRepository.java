package com.azad.supershop.repository;

import com.azad.supershop.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<List<CartEntity>> findByCustomerId(Long customerId);
}
