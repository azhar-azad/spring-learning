package com.azad.demo_spring_vue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoEntityRepository extends JpaRepository<DemoEntity, Long> {
}
