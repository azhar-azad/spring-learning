package com.azad.basicecommerce.productservice.services.category;

import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.common.exceptions.ResourceNotFoundException;
import com.azad.basicecommerce.productservice.models.category.CategoryDto;
import com.azad.basicecommerce.productservice.models.category.CategoryEntity;
import com.azad.basicecommerce.productservice.models.productline.ProductLine;
import com.azad.basicecommerce.productservice.models.productline.ProductLineEntity;
import com.azad.basicecommerce.productservice.repositories.CategoryRepository;
import com.azad.basicecommerce.productservice.repositories.ProductLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private ProductLineRepository productLineRepository;

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDto create(CategoryDto dto) {

        apiUtils.printInfoLog("*** CATEGORY :: CREATE ***");

        Optional<ProductLineEntity> byProductLineUid = productLineRepository.findByProductLineUid(dto.getProductLineUid());
        if (!byProductLineUid.isPresent())
            throw new ResourceNotFoundException("ProductLine", dto.getProductLineUid(), "Resource Not Found");

        CategoryEntity entityFromDto = modelMapper.map(dto, CategoryEntity.class);
        entityFromDto.setCategoryUid(apiUtils.getHash("category", dto.getCategoryName()));
        entityFromDto.setProductLine(byProductLineUid.get());

        CategoryEntity savedEntity = repository.save(entityFromDto);

        CategoryDto dtoToReturn = modelMapper.map(savedEntity, CategoryDto.class);
        dtoToReturn.setProductLine(modelMapper.map(byProductLineUid.get(), ProductLine.class));

        return dtoToReturn;
    }

    @Override
    public List<CategoryDto> getAll(PagingAndSorting ps) {

        apiUtils.printInfoLog("*** CATEGORY :: GET ALL ***");

        List<CategoryEntity> entitiesFromDb = repository.findAll(apiUtils.getPageable(ps)).getContent();
        if (entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> {
                    CategoryDto dto = modelMapper.map(entity, CategoryDto.class);
                    dto.setProductLine(modelMapper.map(entity.getProductLine(), ProductLine.class));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {

        apiUtils.printInfoLog("*** CATEGORY :: GET BY ID ***");

        CategoryEntity entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found", "CATEGORY", "id = " + id));

        CategoryDto dto = modelMapper.map(entity, CategoryDto.class);
        dto.setProductLine(modelMapper.map(entity.getProductLine(), ProductLine.class));

        return dto;
    }

    @Override
    public CategoryDto getByUid(String uid) {

        apiUtils.printInfoLog("*** CATEGORY :: GET BY UID ***");

        CategoryEntity entity = repository.findByCategoryUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found", "CATEGORY", "uid = " + uid));

        CategoryDto dto = modelMapper.map(entity, CategoryDto.class);
        dto.setProductLine(modelMapper.map(entity.getProductLine(), ProductLine.class));

        return dto;
    }

    @Override
    public CategoryDto updateById(Long id, CategoryDto updatedDto) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public CategoryDto updateByUid(String uid, CategoryDto updatedDto) {

        apiUtils.printInfoLog("*** CATEGORY :: UPDATE BY UID ***");

        CategoryEntity entity = repository.findByCategoryUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found", "CATEGORY", "uid = " + uid));

        if (updatedDto.getCategoryName() != null)
            entity.setCategoryName(updatedDto.getCategoryName());

        CategoryEntity updatedEntity = repository.save(entity);

        CategoryDto dto = modelMapper.map(updatedEntity, CategoryDto.class);
        dto.setProductLine(modelMapper.map(updatedEntity.getProductLine(), ProductLine.class));

        return dto;
    }

    @Override
    public void deleteById(Long id) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public void deleteByUid(String uid) {

        apiUtils.printInfoLog("*** CATEGORY :: DELETE BY UID ***");

        CategoryEntity entity = repository.findByCategoryUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found", "CATEGORY", "uid = " + uid));

        repository.delete(entity);
    }

    @Override
    public Long getEntityCount() {
        return repository.count();
    }
}
