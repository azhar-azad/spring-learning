package com.azad.playstoreapi.repos;

import com.azad.playstoreapi.models.entities.PlayStoreUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayStoreUserRepository extends PagingAndSortingRepository<PlayStoreUserEntity, Long> {

    Optional<PlayStoreUserEntity> findByEmail(String email);
    Optional<PlayStoreUserEntity> findByUsername(String username);
}
