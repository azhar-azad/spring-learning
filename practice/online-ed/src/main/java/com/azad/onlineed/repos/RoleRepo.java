package com.azad.onlineed.repos;

import com.azad.onlineed.security.entities.RoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends PagingAndSortingRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(String roleName);
}
