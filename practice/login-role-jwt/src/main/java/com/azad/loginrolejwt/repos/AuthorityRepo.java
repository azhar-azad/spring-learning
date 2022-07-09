package com.azad.loginrolejwt.repos;

import com.azad.loginrolejwt.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AuthorityRepo extends JpaRepository<Authority, Integer> {

    Optional<Authority> findByName(String name);
}
