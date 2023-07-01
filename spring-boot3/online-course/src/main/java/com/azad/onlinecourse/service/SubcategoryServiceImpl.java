package com.azad.onlinecourse.service;

import com.azad.onlinecourse.common.PagingAndSorting;
import com.azad.onlinecourse.models.subcategory.SubcategoryDto;
import com.azad.onlinecourse.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository repository;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public SubcategoryDto create(SubcategoryDto dto) {
        return null;
    }

    @Override
    public List<SubcategoryDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public SubcategoryDto getById(Long id) {
        return null;
    }

    @Override
    public SubcategoryDto updateById(Long id, SubcategoryDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
