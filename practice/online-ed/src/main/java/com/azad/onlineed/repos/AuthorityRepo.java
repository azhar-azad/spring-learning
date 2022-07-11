package com.azad.onlineed.repos;

import com.azad.onlineed.security.entities.AuthorityEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepo extends PagingAndSortingRepository<AuthorityEntity, Integer> {

    Optional<AuthorityEntity> findByAuthorityName(String authorityName);
}
