package com.azad.school.repos;

import com.azad.school.models.entities.StudentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepo extends PagingAndSortingRepository<StudentEntity, Long> {
}
