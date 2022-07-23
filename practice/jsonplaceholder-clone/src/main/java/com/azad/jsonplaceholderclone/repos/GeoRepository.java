package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.GeoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoRepository extends PagingAndSortingRepository<GeoEntity, Long> {
}
