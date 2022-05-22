package com.azad.cineplex2.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex2.entities.Movie;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

}
