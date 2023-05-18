package com.azad.basicecommerce.productservice.services.review;

import com.azad.basicecommerce.common.GenericApiService;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.review.ReviewDto;

import java.util.List;

public interface ReviewService extends GenericApiService<ReviewDto> {

    List<ReviewDto> getAllByProduct(String productUid, PagingAndSorting ps);
}
