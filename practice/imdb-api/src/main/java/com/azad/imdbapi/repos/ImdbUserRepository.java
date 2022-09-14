package com.azad.imdbapi.repos;

import com.azad.imdbapi.entities.ImdbUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImdbUserRepository extends PagingAndSortingRepository<ImdbUserEntity, Long> {

    Optional<ImdbUserEntity> findByUsername(String username);

    Optional<ImdbUserEntity> findByEmail(String email);
}
