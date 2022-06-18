package com.azad.musiclibrary.repositories;

import com.azad.musiclibrary.models.entities.AlbumEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends PagingAndSortingRepository<AlbumEntity, Long> {
}
