package com.azad.basicecommerce.productservice.services.productline;

import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.productservice.models.productline.ProductLineDto;
import com.azad.basicecommerce.productservice.models.productline.ProductLineEntity;
import com.azad.basicecommerce.productservice.repositories.ProductLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductLineServiceImpl implements ProductLineService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    private final ProductLineRepository repository;

    @Autowired
    public ProductLineServiceImpl(ProductLineRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductLineDto create(ProductLineDto dto) {

        apiUtils.logInfo("*** PRODUCTLINE :: CREATE ***");

        ProductLineEntity entityFromDto = modelMapper.map(dto, ProductLineEntity.class);
        entityFromDto.setProductLineUid(apiUtils.getHash("productLine", dto.getProductLineName()));

        ProductLineEntity savedEntity = repository.save(entityFromDto);

        return modelMapper.map(savedEntity, ProductLineDto.class);
    }

    @Override
    public List<ProductLineDto> getAll(PagingAndSorting ps) {

        apiUtils.logInfo("*** PRODUCTLINE :: GET ALL ***");

        List<ProductLineEntity> entitiesFromDb = repository.findAll(apiUtils.getPageable(ps)).getContent();
        if (entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> modelMapper.map(entity, ProductLineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductLineDto getById(Long id) {
        return null;
    }

    @Override
    public ProductLineDto getByUid(String uid) {
        return null;
    }

    @Override
    public ProductLineDto updateById(Long id, ProductLineDto updatedDto) {
        return null;
    }

    @Override
    public ProductLineDto updateByUid(String uid, ProductLineDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByUid(String uid) {

    }

    @Override
    public Long getEntityCount() {
        return repository.count();
    }
}
