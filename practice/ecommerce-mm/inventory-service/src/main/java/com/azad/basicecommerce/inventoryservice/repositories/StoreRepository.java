package com.azad.basicecommerce.inventoryservice.repositories;

import com.azad.basicecommerce.inventoryservice.models.StoreEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<StoreEntity, Long> {

    Optional<StoreEntity> findByStoreUid(String storeUid);
    Optional<List<StoreEntity>> findByStoreOwnerUid(String storeOwnerUid, Pageable pageable);
}
