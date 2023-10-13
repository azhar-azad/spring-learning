package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.people.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity, Long> {

    Optional<PeopleEntity> findByFullName(String fullName);
}
