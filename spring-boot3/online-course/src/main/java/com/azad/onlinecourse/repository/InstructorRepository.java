package com.azad.onlinecourse.repository;

import com.azad.onlinecourse.models.instructor.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {
}
