package com.azad.supershop.repository;

import com.azad.supershop.model.entity.SupplyContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyContractRepository extends JpaRepository<SupplyContractEntity, Long> {

    Optional<List<SupplyContractEntity>> findBySupplierId(Long supplierId);
    Optional<List<SupplyContractEntity>> findByContractDate(LocalDate contractDate);
}
