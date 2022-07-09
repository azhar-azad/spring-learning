package com.azad.loginrolejwt.repos;

import com.azad.loginrolejwt.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
