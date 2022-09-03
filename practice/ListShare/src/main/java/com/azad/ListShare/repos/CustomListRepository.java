package com.azad.ListShare.repos;

import com.azad.ListShare.models.entities.CustomListEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomListRepository extends PagingAndSortingRepository<CustomListEntity, Long> {

    @Query(value = "SELECT * FROM lists WHERE user_id = :userId", nativeQuery = true)
    Optional<List<CustomListEntity>> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
