package com.azad.supershop.service.impl;

import com.azad.supershop.common.AppUtils;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.exception.MethodBlockedException;
import com.azad.supershop.model.dto.supplier.SupplierDto;
import com.azad.supershop.model.entity.SupplierEntity;
import com.azad.supershop.repository.SupplierRepository;
import com.azad.supershop.service.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleSupplierService implements SupplierService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private SupplierRepository repository;

    @Override
    public SupplierDto create(SupplierDto dto) {
        SupplierEntity entity = modelMapper.map(dto, SupplierEntity.class);
        return modelMapper.map(repository.save(entity), SupplierDto.class);
    }

    @Override
    public List<SupplierDto> getAll(PagingAndSorting ps) {
        List<SupplierEntity> entities = repository.findAll(appUtils.getPageable(ps)).getContent();
        if (entities.isEmpty())
            return null;
        return entities.stream()
                .map(entity -> modelMapper.map(entity, SupplierDto.class))
                .toList();
    }

    @Override
    public SupplierDto getById(Long id) {
        throw new MethodBlockedException();
    }

    @Override
    public SupplierDto updateById(Long id, SupplierDto updatedDto) {
        SupplierEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));
        if (updatedDto.getPhone() != null) {
            SupplierEntity supplier = repository.findByPhone(updatedDto.getPhone())
                    .orElse(null);
            if (supplier == null)
                entity.setPhone(updatedDto.getPhone());
        }
        if (updatedDto.getSupplierName() != null)
            entity.setSupplierName(updatedDto.getSupplierName());

        return modelMapper.map(repository.save(entity), SupplierDto.class);
    }

    @Override
    public void deleteById(Long id) {
        throw new MethodBlockedException();
    }

    @Override
    public SupplierDto getByPhone(String phone) {
        SupplierEntity entity = repository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with phone: " + phone));
        return modelMapper.map(entity, SupplierDto.class);
    }

    @Override
    public void deleteByPhone(String phone) {
        SupplierEntity entity = repository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with phone: " + phone));
        repository.delete(entity);
    }
}
