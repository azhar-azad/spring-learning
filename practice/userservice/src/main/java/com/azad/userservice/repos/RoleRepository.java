package com.azad.userservice.repos;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.azad.userservice.entities.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

	Role findByName(String name);
}
