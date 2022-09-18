package com.azad.imdbapi.repos;

import com.azad.imdbapi.entities.MovieEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<MovieEntity, Long> {

    List<MovieEntity> findByReleaseYear(String releaseYear, Pageable pageable);
}
