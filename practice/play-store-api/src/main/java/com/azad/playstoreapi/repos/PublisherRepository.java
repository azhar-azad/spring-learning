package com.azad.playstoreapi.repos;

import com.azad.playstoreapi.models.entities.PublisherEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends PagingAndSortingRepository<PublisherEntity, Long> {
}
