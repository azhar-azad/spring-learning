package com.azad.online_shop.service.impl;

import com.azad.online_shop.common.PagingAndSorting;
import com.azad.online_shop.common.Utils;
import com.azad.online_shop.model.dto.products.ProductDto;
import com.azad.online_shop.model.entity.InventoryEntity;
import com.azad.online_shop.model.entity.ProductEntity;
import com.azad.online_shop.model.pojo.Inventory;
import com.azad.online_shop.repository.InventoryRepository;
import com.azad.online_shop.repository.ProductRepository;
import com.azad.online_shop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ModelMapper modelMapper;

    private InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setInventoryRepository(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public ProductDto create(ProductDto dto) {
        ProductEntity productEntity = modelMapper.map(dto, ProductEntity.class);
        productEntity.setProductUniqueName(Utils.generateProductName(
                dto.getProductName(), dto.getBrand(), dto.getSize()));
        ProductEntity savedProductEntity = productRepository.findByProductUniqueName(productEntity.getProductUniqueName())
                .orElse(null);

        if (savedProductEntity != null) {
            // Inventory record exists for the savedProductEntity
            InventoryEntity inventory = savedProductEntity.getInventory();
            // Update the inventory record with new quantity
            inventory.setQuantity(inventory.getQuantity() + dto.getQuantityOnInventory());
            inventoryRepository.save(inventory);
        } else {
            savedProductEntity = productRepository.save(productEntity);
            InventoryEntity inventory = new InventoryEntity();
            inventory.setProduct(savedProductEntity);
            inventory.setQuantity(dto.getQuantityOnInventory());
            InventoryEntity savedInventoryEntity = inventoryRepository.save(inventory);
            savedProductEntity.setInventory(savedInventoryEntity);
        }

        ProductDto savedDto = modelMapper.map(savedProductEntity, ProductDto.class);
        savedDto.setInventory(modelMapper.map(savedProductEntity.getInventory(), Inventory.class));
        return savedDto;
    }

    @Override
    public List<ProductDto> getAll(PagingAndSorting ps) {
        return List.of();
    }

    @Override
    public ProductDto getById(Long id) {
        return null;
    }

    @Override
    public ProductDto updateById(Long id, ProductDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
