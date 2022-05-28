package com.azad.estatement.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azad.estatement.models.entities.UsrEntity;

@Repository
public interface UsrRepository extends PagingAndSortingRepository<UsrEntity, Long> {

	@Query(value = "select u.* from usr u, usr_mapping um, organization org"
			+ " where u.usr_id = um.usr_id"
			+ " and um.org_id = org.org_id"
			+ " and org.org_uniquename = :orgName", nativeQuery = true)
	List<UsrEntity> findUsrsByOrgName(Pageable pageable, @Param("orgName") String orgName);
}
