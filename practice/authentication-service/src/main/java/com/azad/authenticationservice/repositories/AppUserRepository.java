package com.azad.authenticationservice.repositories;

import com.azad.authenticationservice.models.entities.AppUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {
    Optional<AppUserEntity> findByEmail(String email);
    Optional<AppUserEntity> findByUsername(String username);
}
