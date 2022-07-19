package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.MemberProfileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends PagingAndSortingRepository<MemberProfileEntity, Long> {
}
