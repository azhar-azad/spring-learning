package com.azad.basicecommerce.productservice.services.rating;

import com.azad.basicecommerce.common.GenericApiService;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.rating.RatingDto;

import java.util.List;

public interface RatingService extends GenericApiService<RatingDto> {

    List<RatingDto> getAllByProduct(String productId, PagingAndSorting ps);
}
