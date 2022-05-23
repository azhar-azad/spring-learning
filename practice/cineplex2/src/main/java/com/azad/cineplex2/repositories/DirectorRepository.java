package com.azad.cineplex2.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex2.entities.Director;

@Repository
public interface DirectorRepository extends PagingAndSortingRepository<Director, Long> {

	Director findByFullName(String directorFullName);

}
