package com.azad.moneymanagerapi.repositories;

import com.azad.moneymanagerapi.models.entities.AccountGroupEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountGroupRepository extends PagingAndSortingRepository<AccountGroupEntity, Long> {

    Optional<AccountGroupEntity> findByAccountGroupName(String accountGroupName);
}
