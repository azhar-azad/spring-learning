package com.azad.supershop.repository;

import com.azad.supershop.model.entity.CashBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashBoxRepository extends JpaRepository<CashBoxEntity, Integer> {

    Optional<CashBoxEntity> findByBoxName(String boxName);
}
