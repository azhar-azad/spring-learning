package com.azad.todolist.repos;

import com.azad.todolist.models.entities.AppUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends PagingAndSortingRepository<AppUserEntity, Long> {

    Optional<AppUserEntity> findByEmail(String email);

    Optional<AppUserEntity> findByUserId(String userId);

    Optional<List<AppUserEntity>> findByFirstName(String firstName);

    Optional<List<AppUserEntity>> findByLastName(String lastName);

    Optional<List<AppUserEntity>> findByRole(String role);
}
