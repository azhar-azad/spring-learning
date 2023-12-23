package com.azad.supershop.service.impl;

import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.exception.MethodBlockedException;
import com.azad.supershop.model.dto.cashbox.CashBoxDto;
import com.azad.supershop.model.entity.CashBoxEntity;
import com.azad.supershop.repository.CashBoxRepository;
import com.azad.supershop.service.CashBoxService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleCashBoxService implements CashBoxService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CashBoxRepository repository;

    @Override
    public CashBoxDto create(CashBoxDto dto) {

        CashBoxEntity entity = modelMapper.map(dto, CashBoxEntity.class);
        entity.setCurrentAmount(dto.getInitialAmount());
        entity.setTransactionCount(0L);

        CashBoxEntity savedEntity = repository.save(entity);
        return modelMapper.map(savedEntity, CashBoxDto.class);
    }

    @Override
    public List<CashBoxDto> getAll(PagingAndSorting ps) {
        throw new MethodBlockedException();
    }

    @Override
    public CashBoxDto getById(Long id) {

        CashBoxEntity entity = repository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("CashBox not found with id: " + id));

        return modelMapper.map(entity, CashBoxDto.class);
    }

    @Override
    public CashBoxDto updateById(Long id, CashBoxDto updatedDto) {
        throw new MethodBlockedException();
    }

    @Override
    public void deleteById(Long id) {
        throw new MethodBlockedException();
    }

    @Override
    public CashBoxDto getByName(String cashBoxName) {

        CashBoxEntity entity = repository.findByBoxName(cashBoxName)
                .orElseThrow(() -> new EntityNotFoundException("CashBox not found with name: " + cashBoxName));

        return modelMapper.map(entity, CashBoxDto.class);
    }

    @Override
    public Double getTotalAmount() {
        List<CashBoxEntity> cashBoxes = repository.findAll();
        return cashBoxes.stream()
                .map(CashBoxEntity::getCurrentAmount)
                .reduce(0.0, Double::sum);
    }
}
