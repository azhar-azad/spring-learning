package com.azad.school.repos;

import com.azad.school.models.entities.ClassEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClassRepo extends PagingAndSortingRepository<ClassEntity, Long> {
}
