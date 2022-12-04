package com.azad.wcstatapi.repos;

import com.azad.wcstatapi.models.entities.GroupEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByWcGroupName(String wcGroupName);
}
