package com.azad.cineplex.repos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.cineplex.entities.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

}
