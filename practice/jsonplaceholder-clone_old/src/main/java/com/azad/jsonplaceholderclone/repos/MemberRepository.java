package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUsername(String username);
}
