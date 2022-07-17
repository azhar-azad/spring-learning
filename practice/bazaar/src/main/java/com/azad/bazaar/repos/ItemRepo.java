package com.azad.bazaar.repos;

import com.azad.bazaar.models.entities.ItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends PagingAndSortingRepository<ItemEntity, Long> {
}
