package com.azad.supershop.service.impl;

import com.azad.supershop.common.AppUtils;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.product.ProductDto;
import com.azad.supershop.model.dto.supplier.SupplierDto;
import com.azad.supershop.model.dto.supplycontract.SupplyContractDto;
import com.azad.supershop.model.entity.ProductEntity;
import com.azad.supershop.model.entity.SupplierEntity;
import com.azad.supershop.model.entity.SupplyContractEntity;
import com.azad.supershop.model.pojo.Category;
import com.azad.supershop.model.pojo.Supplier;
import com.azad.supershop.repository.SupplierRepository;
import com.azad.supershop.repository.SupplyContractRepository;
import com.azad.supershop.service.SupplyContractService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SimpleSupplyContractService implements SupplyContractService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplyContractRepository repository;

    @Override
    public SupplyContractDto create(SupplyContractDto dto) {
        SupplierEntity supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
        SupplyContractEntity entity = modelMapper.map(dto, SupplyContractEntity.class);
        entity.setUnit(dto.getUnit().getValue());
        entity.setSupplier(supplier);
        return getDtoFromEntity(repository.save(entity));
    }

    @Override
    public List<SupplyContractDto> getAll(PagingAndSorting ps) {
        List<SupplyContractEntity> entities = repository.findAll(appUtils.getPageable(ps)).getContent();
        if (entities.isEmpty())
            return null;
        return entities.stream()
                .map(this::getDtoFromEntity)
                .toList();
    }

    @Override
    public SupplyContractDto getById(Long id) {
        return getDtoFromEntity(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SupplyContract not found with id: " + id)));
    }

    @Override
    public SupplyContractDto updateById(Long id, SupplyContractDto updatedDto) {
        SupplyContractEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SupplyContract not found with id: " + id));
        if (updatedDto.getContractDate() != null)
            entity.setContractDate(updatedDto.getContractDate());
        if (updatedDto.getUnit() != null)
            entity.setUnit(updatedDto.getUnit().getValue());
        if (updatedDto.getQuantity() != null)
            entity.setQuantity(updatedDto.getQuantity());
        if (updatedDto.getTotalCost() != null)
            entity.setTotalCost(updatedDto.getTotalCost());

        return getDtoFromEntity(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        SupplyContractEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SupplyContract not found with id: " + id));
        repository.delete(entity);
    }

    @Override
    public List<SupplyContractDto> getAllBySupplier(Long supplierId, PagingAndSorting ps) {
        List<SupplyContractEntity> entities = repository.findBySupplierId(supplierId, appUtils.getPageable(ps))
                .orElse(null);
        if (entities == null)
            return null;
        return entities.stream()
                .map(this::getDtoFromEntity)
                .toList();
    }

    @Override
    public List<SupplyContractDto> getAllByContractDate(LocalDate contractDate, PagingAndSorting ps) {
        List<SupplyContractEntity> entities = repository.findByContractDate(contractDate, appUtils.getPageable(ps))
                .orElse(null);
        if (entities == null)
            return null;
        return entities.stream()
                .map(this::getDtoFromEntity)
                .toList();
    }

    private SupplyContractDto getDtoFromEntity(SupplyContractEntity entity) {
        SupplyContractDto dto = modelMapper.map(entity, SupplyContractDto.class);
        dto.setSupplier(modelMapper.map(entity.getSupplier(), Supplier.class));
        return dto;
    }
}
