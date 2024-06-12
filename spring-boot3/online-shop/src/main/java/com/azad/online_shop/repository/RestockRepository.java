package com.azad.online_shop.repository;

import com.azad.online_shop.model.entity.RestockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestockRepository extends JpaRepository<RestockEntity, Long> {
}
