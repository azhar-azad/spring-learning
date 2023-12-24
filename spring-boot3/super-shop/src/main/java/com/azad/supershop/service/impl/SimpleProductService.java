package com.azad.supershop.service.impl;

import com.azad.supershop.common.AppUtils;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.product.ProductDto;
import com.azad.supershop.model.entity.CategoryEntity;
import com.azad.supershop.model.entity.ProductEntity;
import com.azad.supershop.model.entity.SupplierEntity;
import com.azad.supershop.model.pojo.Category;
import com.azad.supershop.model.pojo.Supplier;
import com.azad.supershop.repository.CategoryRepository;
import com.azad.supershop.repository.ProductRepository;
import com.azad.supershop.repository.SupplierRepository;
import com.azad.supershop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleProductService implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository repository;

    @Override
    public ProductDto create(ProductDto dto) {
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + dto.getCategoryId()));
        SupplierEntity supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
        ProductEntity entity = modelMapper.map(dto, ProductEntity.class);
        entity.setCategory(category);
        entity.setSupplier(supplier);

        return modelMapper.map(repository.save(entity), ProductDto.class);
    }

    @Override
    public List<ProductDto> getAll(PagingAndSorting ps) {
        List<ProductEntity> entities = repository.findAll(appUtils.getPageable(ps)).getContent();
        if (entities.isEmpty())
            return null;
        return entities.stream()
                .map(this::getDtoFromEntity)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return getDtoFromEntity(entity);
    }

    @Override
    public ProductDto updateById(Long id, ProductDto updatedDto) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        if (updatedDto.getProductName() != null) {
            ProductEntity product = repository.findByProductName(updatedDto.getProductName())
                    .orElse(null);
            if (product == null)
                entity.setProductName(updatedDto.getProductName());
        }
        if (updatedDto.getPrice() != null)
            entity.setPrice(updatedDto.getPrice());
        if (updatedDto.getStockQuantity() != null)
            entity.setStockQuantity(updatedDto.getStockQuantity());
        if (updatedDto.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findById(updatedDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + updatedDto.getCategoryId()));
            entity.setCategory(category);
        }
        if (updatedDto.getSupplierId() != null) {
            SupplierEntity supplier = supplierRepository.findById(updatedDto.getSupplierId())
                    .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + updatedDto.getSupplierId()));
            entity.setSupplier(supplier);
        }

        return modelMapper.map(repository.save(entity), ProductDto.class);
    }

    @Override
    public void deleteById(Long id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        repository.delete(entity);
    }

    @Override
    public List<ProductDto> getAllByCategory(Long categoryId, PagingAndSorting ps) {
        List<ProductEntity> entities =
                repository.findByCategoryId(categoryId, appUtils.getPageable(ps)).orElse(null);
        if (entities == null)
            return null;
        return entities.stream()
                .map(this::getDtoFromEntity)
                .toList();
    }

    @Override
    public ProductDto getByProductName(String productName) {
        productName = String.join(" ", productName.split("_"));
        String fixedProductName = "`" + productName + "`";
        ProductEntity entity = repository.findByProductName(productName)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with name: " + fixedProductName));
        return getDtoFromEntity(entity);
    }

    private ProductDto getDtoFromEntity(ProductEntity entity) {
        ProductDto dto = modelMapper.map(entity, ProductDto.class);
        dto.setCategory(modelMapper.map(entity.getCategory(), Category.class));
        dto.setSupplier(modelMapper.map(entity.getSupplier(), Supplier.class));
        return dto;
    }
}
