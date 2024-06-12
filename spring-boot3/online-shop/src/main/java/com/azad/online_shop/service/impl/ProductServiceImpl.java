package com.azad.online_shop.service.impl;

import com.azad.online_shop.common.PagingAndSorting;
import com.azad.online_shop.common.Utils;
import com.azad.online_shop.model.dto.products.ProductDto;
import com.azad.online_shop.model.entity.ProductEntity;
import com.azad.online_shop.repository.ProductRepository;
import com.azad.online_shop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ModelMapper modelMapper;

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto create(ProductDto dto) {
        ProductEntity productEntity = modelMapper.map(dto, ProductEntity.class);
        productEntity.setProductUniqueName(Utils.generateProductName(dto.getProductName(), dto.getBrand(), dto.getSize()));
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        return modelMapper.map(savedProductEntity, ProductDto.class);
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
