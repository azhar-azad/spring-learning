package com.azad.simcity.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azad.simcity.entities.CommercialItem;

@Repository
public interface CommercialItemRepository extends JpaRepository<CommercialItem, Long> {

}
