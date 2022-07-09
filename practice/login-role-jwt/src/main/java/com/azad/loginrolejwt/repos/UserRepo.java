package com.azad.loginrolejwt.repos;

import com.azad.loginrolejwt.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
