package com.azad.ecommerce.inventoryservice.repositories;

import com.azad.ecommerce.inventoryservice.models.entities.StoreEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<StoreEntity, Long> {

    Optional<StoreEntity> findByStoreUid(Long storeUid);
}
