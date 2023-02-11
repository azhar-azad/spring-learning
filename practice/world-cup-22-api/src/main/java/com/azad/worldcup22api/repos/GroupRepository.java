package com.azad.worldcup22api.repos;

import com.azad.worldcup22api.models.entities.GroupEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<GroupEntity, Long> {
}
