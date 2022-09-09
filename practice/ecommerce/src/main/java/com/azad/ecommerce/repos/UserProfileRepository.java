package com.azad.ecommerce.repos;

import com.azad.ecommerce.models.entities.UserProfileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends PagingAndSortingRepository<UserProfileEntity, Long> {

    long count();
}
