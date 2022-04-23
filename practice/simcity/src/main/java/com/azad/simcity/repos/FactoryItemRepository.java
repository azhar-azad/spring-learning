package com.azad.simcity.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azad.simcity.entities.FactoryItem;

@Repository
public interface FactoryItemRepository extends JpaRepository<FactoryItem, Long> {

}
