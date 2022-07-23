package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.CompanyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, Long> {
}
