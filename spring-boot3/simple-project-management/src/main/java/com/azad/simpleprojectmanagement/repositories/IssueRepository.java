package com.azad.simpleprojectmanagement.repositories;

import com.azad.simpleprojectmanagement.models.issue.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
}
