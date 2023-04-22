package com.azad.moneymanagerapi.services.impl;

import com.azad.moneymanagerapi.commons.AppUtils;
import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.SubcategoryDto;
import com.azad.moneymanagerapi.models.entities.CategoryEntity;
import com.azad.moneymanagerapi.models.entities.SubcategoryEntity;
import com.azad.moneymanagerapi.repositories.CategoryRepository;
import com.azad.moneymanagerapi.repositories.SubcategoryRepository;
import com.azad.moneymanagerapi.services.SubcategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private CategoryRepository categoryRepository;

    private final SubcategoryRepository repository;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public SubcategoryDto create(Long categoryId, SubcategoryDto dto) {

        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new RuntimeException("Category not found with id: " + categoryId));

        SubcategoryEntity entity = modelMapper.map(dto, SubcategoryEntity.class);
        entity.setCategory(category);

        SubcategoryEntity savedEntity = repository.save(entity);

        SubcategoryDto savedDto = modelMapper.map(savedEntity, SubcategoryDto.class);
        savedDto.setCategoryId(savedEntity.getCategory().getId());

        return savedDto;
    }

    @Override
    public List<SubcategoryDto> getAllByParent(Long categoryId, PagingAndSorting ps) {

        Pageable pageable = appUtils.getPageable(ps);

        List<SubcategoryEntity> entitiesFromDb = repository.findByCategoryId(categoryId, pageable).orElse(null);
        if (entitiesFromDb == null || entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> {
                    SubcategoryDto dto = modelMapper.map(entity, SubcategoryDto.class);
                    dto.setCategoryId(entity.getCategory().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryDto create(SubcategoryDto dto) {
        throw new RuntimeException("Creating Subcategory without a parent Category is prohibited.");
    }

    @Override
    public List<SubcategoryDto> getAll(PagingAndSorting ps) {

        Pageable pageable = appUtils.getPageable(ps);

        List<SubcategoryEntity> entitiesFromDb = repository.findAll(pageable).getContent();
        if (entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> {
                    SubcategoryDto dto = modelMapper.map(entity, SubcategoryDto.class);
                    dto.setCategoryId(entity.getCategory().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryDto getById(Long id) {

        SubcategoryEntity entityFromDb = repository.findById(id).orElse(null);
        if (entityFromDb == null)
            return null;

        SubcategoryDto dto = modelMapper.map(entityFromDb, SubcategoryDto.class);
        dto.setCategoryId(entityFromDb.getCategory().getId());
        return dto;
    }

    @Override
    public SubcategoryDto updateById(Long id, SubcategoryDto updatedDto) {

        SubcategoryEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Subcategory not found with id: " + id));

        if (updatedDto.getSubcategoryName() != null)
            entityFromDb.setSubcategoryName(updatedDto.getSubcategoryName());

        SubcategoryEntity updatedEntity = repository.save(entityFromDb);

        SubcategoryDto dto = modelMapper.map(updatedEntity, SubcategoryDto.class);
        dto.setCategoryId(updatedEntity.getCategory().getId());
        return dto;
    }

    @Override
    public void deleteById(Long id) {

        SubcategoryEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Subcategory not found with id: " + id));

        repository.delete(entityFromDb);
    }
}
