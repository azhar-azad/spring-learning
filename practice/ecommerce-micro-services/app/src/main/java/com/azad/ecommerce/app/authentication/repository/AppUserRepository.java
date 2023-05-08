package com.azad.ecommerce.app.authentication.repository;

import com.azad.ecommerce.app.authentication.models.entities.AppUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {

    Optional<AppUserEntity> findByUserUid(Long userUid);
    Optional<AppUserEntity> findByEmail(String email);
    Optional<AppUserEntity> findByUsername(String username);
}
