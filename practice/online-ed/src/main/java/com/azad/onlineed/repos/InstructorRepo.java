package com.azad.onlineed.repos;

import com.azad.onlineed.models.entities.InstructorEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepo extends PagingAndSortingRepository<InstructorEntity, Long> {
}
