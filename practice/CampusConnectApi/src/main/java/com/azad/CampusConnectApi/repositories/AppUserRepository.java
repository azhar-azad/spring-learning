package com.azad.CampusConnectApi.repositories;

import com.azad.CampusConnectApi.models.entities.AppUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {
}
