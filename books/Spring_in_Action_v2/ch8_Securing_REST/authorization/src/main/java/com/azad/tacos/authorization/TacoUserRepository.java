package com.azad.tacos.authorization;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoUserRepository extends JpaRepository<TacoUser, Long> {

    TacoUser findByUsername(String username);
}
