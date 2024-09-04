package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.WriterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WriterRepository extends JpaRepository<WriterEntity, Long> {

    Optional<WriterEntity> findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
}
