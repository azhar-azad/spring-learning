package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.PhotoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends PagingAndSortingRepository<PhotoEntity, Long> {
}