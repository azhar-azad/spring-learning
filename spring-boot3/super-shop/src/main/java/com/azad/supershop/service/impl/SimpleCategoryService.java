package com.azad.supershop.service.impl;

import com.azad.supershop.common.AppUtils;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.category.CategoryDto;
import com.azad.supershop.model.entity.CategoryEntity;
import com.azad.supershop.repository.CategoryRepository;
import com.azad.supershop.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleCategoryService implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private CategoryRepository repository;

    @Override
    public CategoryDto create(CategoryDto dto) {
        CategoryEntity entity = modelMapper.map(dto, CategoryEntity.class);
        return modelMapper.map(repository.save(entity), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAll(PagingAndSorting ps) {
        List<CategoryEntity> entities = repository.findAll(appUtils.getPageable(ps)).getContent();
        if (entities.isEmpty())
            return null;
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CategoryDto.class))
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        CategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return modelMapper.map(entity, CategoryDto.class);
    }

    @Override
    public CategoryDto updateById(Long id, CategoryDto updatedDto) {
        CategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        if (updatedDto.getCategoryName() != null) {
            CategoryEntity category = repository.findByCategoryName(updatedDto.getCategoryName())
                    .orElse(null);
            if (category == null)
                entity.setCategoryName(updatedDto.getCategoryName());
        }
        if (updatedDto.getDescription() != null)
            entity.setDescription(updatedDto.getDescription());

        return modelMapper.map(repository.save(entity), CategoryDto.class);
    }

    @Override
    public void deleteById(Long id) {
        CategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        repository.delete(entity);
    }
}
