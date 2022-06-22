package com.azad.todolist.repos;

import com.azad.todolist.models.entities.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUserEntity, Long> {

    public Optional<AppUserEntity> findByEmail(String email);
}
