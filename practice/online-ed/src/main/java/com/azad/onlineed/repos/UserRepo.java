package com.azad.onlineed.repos;

import com.azad.onlineed.security.entities.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
