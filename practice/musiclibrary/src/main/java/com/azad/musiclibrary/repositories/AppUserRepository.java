package com.azad.musiclibrary.repositories;

import com.azad.musiclibrary.models.entities.AppUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {
}
