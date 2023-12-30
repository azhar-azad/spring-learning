package com.azad.supershop.repository;

import com.azad.supershop.model.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    Optional<SupplierEntity> findByPhone(String phone);
}
