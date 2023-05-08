package com.azad.ecommerce.inventoryservice.services.impl;

import com.azad.ecommerce.inventoryservice.commons.AppUtils;
import com.azad.ecommerce.inventoryservice.commons.PagingAndSorting;
import com.azad.ecommerce.inventoryservice.models.dtos.StoreDto;
import com.azad.ecommerce.inventoryservice.models.entities.StoreEntity;
import com.azad.ecommerce.inventoryservice.repositories.StoreRepository;
import com.azad.ecommerce.inventoryservice.services.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final StoreRepository repository;

    @Autowired
    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public StoreDto create(StoreDto dto) {

        StoreEntity entityFromDto = modelMapper.map(dto, StoreEntity.class);
        entityFromDto.setStoreUid(appUtils.getHash(dto.getStoreName()));
        entityFromDto.setStoreOwnerUid(appUtils.getUserUid());

        StoreEntity savedEntity = repository.save(entityFromDto);

        return modelMapper.map(savedEntity, StoreDto.class);
    }

    @Override
    public List<StoreDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public StoreDto getById(Long id) {
        return null;
    }

    @Override
    public StoreDto updateById(Long id, StoreDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
