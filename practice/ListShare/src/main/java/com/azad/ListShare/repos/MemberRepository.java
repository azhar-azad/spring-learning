package com.azad.ListShare.repos;

import com.azad.ListShare.models.entities.MemberEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUsername(String username);
}
