package com.azad.moneymanagerapi.repositories;

import com.azad.moneymanagerapi.models.entities.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<TransactionEntity, Long> {

    Optional<List<TransactionEntity>> findByAccountId(Long accountId);
    Optional<List<TransactionEntity>> findByCategoryId(Long categoryId);
    Optional<List<TransactionEntity>> findBySubcategoryId(Long subcategoryId);
    Optional<List<TransactionEntity>> findByTransactionType(String transactionType, Pageable pageable);
}
