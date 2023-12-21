package com.azad.supershop.repository;

import com.azad.supershop.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Optional<List<TransactionEntity>> findByCustomerId(Long customerId);

    Optional<List<TransactionEntity>> findByDate(LocalDate date);
}
