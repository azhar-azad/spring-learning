package com.azad.moneymanagerapi.repositories;

import com.azad.moneymanagerapi.models.entities.AccountEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long> {

    Optional<List<AccountEntity>> findByAccountGroupId(Long accountGroupId);
}
