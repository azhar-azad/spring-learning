package com.azad.estatement.repos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.estatement.models.entities.OrganizationEntity;

@Repository
public interface OrgRepository extends PagingAndSortingRepository<OrganizationEntity, Long> {

}
