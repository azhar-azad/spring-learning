package com.azad.cineplex.repos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex.entities.Hall;

@Repository
public interface HallRepository extends PagingAndSortingRepository<Hall, Long> {

}
