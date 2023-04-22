package com.azad.moneymanagerapi.services;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.SubcategoryDto;

import java.util.List;

public interface SubcategoryService extends GenericApiService<SubcategoryDto> {

    SubcategoryDto create(Long categoryId, SubcategoryDto dto);
    List<SubcategoryDto> getAllByParent(Long categoryId, PagingAndSorting ps);
}
