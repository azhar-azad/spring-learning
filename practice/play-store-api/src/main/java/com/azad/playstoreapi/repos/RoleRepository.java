package com.azad.playstoreapi.repos;

import com.azad.playstoreapi.models.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(String roleName);
}
