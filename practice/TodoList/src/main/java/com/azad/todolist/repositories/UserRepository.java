package com.azad.todolist.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.todolist.entities.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUsername(String username);
}
