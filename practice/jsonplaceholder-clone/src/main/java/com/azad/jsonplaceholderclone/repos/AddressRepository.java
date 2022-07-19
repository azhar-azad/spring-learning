package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.AddressEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {
}
