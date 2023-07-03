package com.azad.hosteldiningapi.repositories;

import com.azad.hosteldiningapi.models.memberinfo.MemberInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long> {

    Optional<MemberInfoEntity> findByUid(String uid);
}
