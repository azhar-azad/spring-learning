package com.azad.jsonplaceholder.repos;

import com.azad.jsonplaceholder.models.entities.MemberProfileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends PagingAndSortingRepository<MemberProfileEntity, Long> {

    long count();
}
