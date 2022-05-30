package com.azad.bankapi.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.bankapi.models.entities.BankUserEntity;

@Repository
public interface BankUserRepository extends PagingAndSortingRepository<BankUserEntity, Long> {

}
