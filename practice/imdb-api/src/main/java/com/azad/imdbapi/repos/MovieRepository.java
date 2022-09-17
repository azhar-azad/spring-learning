package com.azad.imdbapi.repos;

import com.azad.imdbapi.entities.MovieEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<MovieEntity, Long> {
}
