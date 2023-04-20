package com.azad.moneymanagerapi.repositories;

import com.azad.moneymanagerapi.models.entities.TransferEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends PagingAndSortingRepository<TransferEntity, Long> {

    Optional<List<TransferEntity>> findByFromAccount(String fromAccount);
    Optional<List<TransferEntity>> findByToAccount(String toAccount);
}
