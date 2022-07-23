package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.security.entities.RoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(String roleName);
}
