package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.WriterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<WriterEntity, Long> {
}
