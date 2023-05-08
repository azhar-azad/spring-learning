package com.azad.ecommerce.productservice.repositories;

import com.azad.ecommerce.productservice.models.entities.ReviewEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByReviewUid(Long reviewUid);
    Optional<List<ReviewEntity>> findByProductUid(Long productUid);
    Optional<List<ReviewEntity>> findByUserUid(Long userUid);
}
