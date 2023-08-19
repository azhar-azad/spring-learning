package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.memberinfo.MemberInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long> {
}
