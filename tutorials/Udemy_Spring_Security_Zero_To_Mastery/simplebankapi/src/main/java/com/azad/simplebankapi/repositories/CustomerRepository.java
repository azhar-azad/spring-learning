package com.azad.simplebankapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azad.simplebankapi.entities.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findByEmail(String email);
}
