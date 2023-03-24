package com.azad.playstoreapi.services.impl;

import com.azad.playstoreapi.models.dtos.CategoryDto;
import com.azad.playstoreapi.models.entities.CategoryEntity;
import com.azad.playstoreapi.repos.CategoryRepository;
import com.azad.playstoreapi.security.auth.AuthService;
import com.azad.playstoreapi.services.CategoryService;
import com.azad.playstoreapi.utils.AppUtils;
import com.azad.playstoreapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDto create(CategoryDto request) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only Admins can create new Category");
        }

        CategoryEntity entity = modelMapper.map(request, CategoryEntity.class);
        CategoryEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public CategoryDto getById(Long id) {
        return null;
    }

    @Override
    public CategoryDto updateById(Long id, CategoryDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
