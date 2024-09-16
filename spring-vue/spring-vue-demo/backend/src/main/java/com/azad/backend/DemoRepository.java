package com.azad.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemoRepository extends JpaRepository<Demo, Long> {

    Optional<Demo> findByTitle(String title);
}
