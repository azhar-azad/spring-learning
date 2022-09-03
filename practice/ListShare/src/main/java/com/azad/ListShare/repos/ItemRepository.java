package com.azad.ListShare.repos;

import com.azad.ListShare.models.entities.ItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<ItemEntity, Long> {
}
