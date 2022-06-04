package com.azad.CampusConnectApi.repositories;

import com.azad.CampusConnectApi.models.entities.ProfileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, Long> {

    ProfileEntity findByAppUserId(Long appUserId);
}
