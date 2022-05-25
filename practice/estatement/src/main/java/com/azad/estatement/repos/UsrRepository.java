package com.azad.estatement.repos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.estatement.models.entities.UsrEntity;

@Repository
public interface UsrRepository extends PagingAndSortingRepository<UsrEntity, Long> {

}
