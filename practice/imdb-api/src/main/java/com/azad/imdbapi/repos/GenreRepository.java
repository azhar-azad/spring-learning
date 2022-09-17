package com.azad.imdbapi.repos;

import com.azad.imdbapi.entities.GenreEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends PagingAndSortingRepository<GenreEntity, Long> {

    Optional<GenreEntity> findByGenreName(String genreName);
}
