package com.azad.cineplex2.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex2.entities.Hall;

@Repository
public interface HallRepository extends PagingAndSortingRepository<Hall, Long> {

}
