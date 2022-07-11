package com.azad.onlineed.repos;

import com.azad.onlineed.models.entities.StudentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends PagingAndSortingRepository<StudentEntity, Long> {
}
