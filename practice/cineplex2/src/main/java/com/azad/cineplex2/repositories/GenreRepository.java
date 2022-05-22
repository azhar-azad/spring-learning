package com.azad.cineplex2.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex2.entities.Genre;

@Repository
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

	Genre findByName(String name);
}
