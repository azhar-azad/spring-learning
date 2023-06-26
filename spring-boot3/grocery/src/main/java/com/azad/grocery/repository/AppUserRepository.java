package com.azad.grocery.repository;

import com.azad.grocery.models.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    Optional<AppUserEntity> findByEmail(String email);
    Optional<AppUserEntity> findByUsername(String username);
}
