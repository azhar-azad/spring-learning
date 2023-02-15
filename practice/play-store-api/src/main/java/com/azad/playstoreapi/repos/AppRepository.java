package com.azad.playstoreapi.repos;

import com.azad.playstoreapi.models.entities.AppEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends PagingAndSortingRepository<AppEntity, Long> {
}
