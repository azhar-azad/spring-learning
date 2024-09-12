package com.azad.spring_demo_docs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoEntityRepository extends JpaRepository<DemoEntity, Long> {
}
