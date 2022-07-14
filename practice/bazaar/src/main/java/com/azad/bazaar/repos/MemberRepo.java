package com.azad.bazaar.repos;

import com.azad.bazaar.security.entities.MemberEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends PagingAndSortingRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByUsername(String username);
}
