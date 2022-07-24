package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.AlbumEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends PagingAndSortingRepository<AlbumEntity, Long> {
}
