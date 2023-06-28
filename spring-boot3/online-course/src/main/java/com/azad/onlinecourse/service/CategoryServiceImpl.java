package com.azad.onlinecourse.service;

import com.azad.onlinecourse.common.exception.ResourceNotFoundException;
import com.azad.onlinecourse.common.exception.UnauthorizedAccessException;
import com.azad.onlinecourse.common.util.ApiUtils;
import com.azad.onlinecourse.common.PagingAndSorting;
import com.azad.onlinecourse.models.category.CategoryDto;
import com.azad.onlinecourse.models.category.CategoryEntity;
import com.azad.onlinecourse.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private ApiUtils apiUtils;

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    private void validateUserAccess(String errorLog, String message) {
        if (!authService.loggedInUserIsAdmin()) {
            apiUtils.logError(CategoryServiceImpl.class, errorLog);
            throw new UnauthorizedAccessException(message, "ADMIN");
        }
    }

    @Override
    public CategoryDto create(CategoryDto dto) {

        apiUtils.logInfo(CategoryServiceImpl.class, "Category :: CREATE");

        validateUserAccess("Only Admin can create a new Category",
                "Category CREATE >> ERROR -> UNAUTHORIZED ACCESS");

        CategoryEntity entity = modelMapper.map(dto, CategoryEntity.class);
        entity.setCreatedAt(LocalDateTime.now());

        CategoryEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAll(PagingAndSorting ps) {

        apiUtils.logInfo(CategoryServiceImpl.class, "Category :: GET ALL");

        List<CategoryEntity> entitiesFromDb = repository.findAll(apiUtils.getPageable(ps)).getContent();

        if (entitiesFromDb.size() == 0) {
            return null;
        }

        return entitiesFromDb.stream()
                .map(entity -> modelMapper.map(entity, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {

        apiUtils.logInfo(CategoryServiceImpl.class, "Category :: GET BY ID");

        validateUserAccess("Only Admin can get a Category by id",
                "Category GET BY ID >> ERROR -> UNAUTHORIZED ACCESS");

        CategoryEntity entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category GET >> ERROR -> RESOURCE NOT FOUND",
                        "CATEGORY", "id: " + id));

        return modelMapper.map(entity, CategoryDto.class);
    }

    @Override
    public CategoryDto updateById(Long id, CategoryDto updatedDto) {

        apiUtils.logInfo(CategoryServiceImpl.class, "Category :: UPDATE BY ID");

        validateUserAccess("Only Admin can update a Category by id",
                "Category UPDATE BY ID >> ERROR -> UNAUTHORIZED ACCESS");

        CategoryEntity entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category UPDATE >> ERROR -> RESOURCE NOT FOUND",
                        "CATEGORY", "id: " + id));

        if (updatedDto.getCategoryName() != null)
            entity.setCategoryName(updatedDto.getCategoryName());
        entity.setModifiedAt(LocalDateTime.now());

        CategoryEntity updatedEntity = repository.save(entity);

        return modelMapper.map(updatedEntity, CategoryDto.class);
    }

    @Override
    public void deleteById(Long id) {

        apiUtils.logInfo(CategoryServiceImpl.class, "Category :: DELETE BY ID");

        validateUserAccess("Only Admin can delete a Category by id",
                "Category DELETE BY ID >> ERROR -> UNAUTHORIZED ACCESS");

        CategoryEntity entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category DELETE >> ERROR -> RESOURCE NOT FOUND",
                        "CATEGORY", "id: " + id));

        repository.delete(entity);
    }
}
