package com.azad.ecommerce.repos;

import com.azad.ecommerce.models.entities.SupplierEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends PagingAndSortingRepository<SupplierEntity, Long> {
}
