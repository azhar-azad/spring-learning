package com.azad.cineplex2.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex2.entities.Cast;

@Repository
public interface CastRepository extends PagingAndSortingRepository<Cast, Long> {

	Cast findByFullName(String castFullName);

}
