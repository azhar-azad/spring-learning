package com.azad.moneymanagerapi.services.impl;

import com.azad.moneymanagerapi.commons.AppUtils;
import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.CategoryDto;
import com.azad.moneymanagerapi.models.entities.CategoryEntity;
import com.azad.moneymanagerapi.repositories.CategoryRepository;
import com.azad.moneymanagerapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDto create(CategoryDto dto) {

        dto.setType(dto.getType().toUpperCase());
        CategoryEntity entity = modelMapper.map(dto, CategoryEntity.class);

        CategoryEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<CategoryEntity> allEntitiesFromDb = repository.findAll(pageable).getContent();
        if (allEntitiesFromDb.size() == 0)
            return null;

        return allEntitiesFromDb.stream()
                .map(entity -> modelMapper.map(entity, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {

        CategoryEntity entityFromDb = repository.findById(id).orElse(null);
        if (entityFromDb == null)
            return null;

        return modelMapper.map(entityFromDb, CategoryDto.class);
    }

    @Override
    public CategoryDto updateById(Long id, CategoryDto updatedDto) {

        CategoryEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("AccountGroup not found with id: " + id));

        if (updatedDto.getCategoryName() != null)
            entityFromDb.setCategoryName(updatedDto.getCategoryName());
        if (updatedDto.getType() != null) {
            updatedDto.setType(updatedDto.getType().toUpperCase());
            entityFromDb.setType(updatedDto.getType());
        }

        CategoryEntity updatedEntity = repository.save(entityFromDb);

        return modelMapper.map(updatedEntity, CategoryDto.class);
    }

    @Override
    public void deleteById(Long id) {

        CategoryEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("AccountGroup not found with id: " + id));

        repository.delete(entityFromDb);
    }
}
