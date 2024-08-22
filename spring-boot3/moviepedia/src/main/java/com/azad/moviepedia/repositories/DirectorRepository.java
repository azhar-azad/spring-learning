package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {

    Optional<DirectorEntity> findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
}
