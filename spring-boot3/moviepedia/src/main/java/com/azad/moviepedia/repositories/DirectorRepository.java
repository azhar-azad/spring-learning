package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {
}
