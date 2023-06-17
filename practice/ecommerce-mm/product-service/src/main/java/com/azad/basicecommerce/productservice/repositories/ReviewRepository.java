package com.azad.basicecommerce.productservice.repositories;

import com.azad.basicecommerce.productservice.models.review.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByReviewUid(String reviewUid);
    Optional<List<ReviewEntity>> findByProductId(Long productId, Pageable pageable);
    Optional<List<ReviewEntity>> findByReviewerUid(String reviewerUid, Pageable pageable);
}
