package com.azad.school.repos;

import com.azad.school.models.entities.TeacherEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeacherRepo extends PagingAndSortingRepository<TeacherEntity, Long> {
}
