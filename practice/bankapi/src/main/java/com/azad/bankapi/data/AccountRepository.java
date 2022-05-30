package com.azad.bankapi.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.bankapi.models.entities.AccountEntity;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long> {

}
