package com.azad.hosteldiningapi.repositories;

import com.azad.hosteldiningapi.models.auth.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUid(String uid);
    Optional<List<MemberEntity>> findByRoleId(Long roleId);
    Optional<MemberEntity> findByEmail(String email);
}
