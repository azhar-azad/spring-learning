package com.azad.imdbapi.repos;

import com.azad.imdbapi.entities.AuthRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRoleRepository extends CrudRepository<AuthRoleEntity, Integer> {

    Optional<AuthRoleEntity> findByRoleName(String roleName);
}
