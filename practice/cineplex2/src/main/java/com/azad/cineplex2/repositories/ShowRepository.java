package com.azad.cineplex2.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex2.entities.Show;

@Repository
public interface ShowRepository extends PagingAndSortingRepository<Show, Long> {

}
