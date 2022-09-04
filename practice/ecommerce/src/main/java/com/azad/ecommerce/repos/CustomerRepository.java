package com.azad.ecommerce.repos;

import com.azad.ecommerce.models.entities.CustomerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Long> {
}
