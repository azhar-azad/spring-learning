package com.azad.cineplex.repos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex.entities.Show;

@Repository
public interface ShowRepository extends PagingAndSortingRepository<Show, Long> {

}
