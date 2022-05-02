package com.azad.userservice.repos;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.azad.userservice.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUsername(String username);
}
