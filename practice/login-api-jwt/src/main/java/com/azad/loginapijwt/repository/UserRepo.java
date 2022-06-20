package com.azad.loginapijwt.repository;

import com.azad.loginapijwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
