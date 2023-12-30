package com.azad.supershop.service.impl;

import com.azad.supershop.common.AppUtils;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.exception.MethodBlockedException;
import com.azad.supershop.model.dto.customer.CustomerDto;
import com.azad.supershop.model.entity.CustomerEntity;
import com.azad.supershop.repository.CustomerRepository;
import com.azad.supershop.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleCustomerService implements CustomerService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private CustomerRepository repository;

    @Override
    public CustomerDto create(CustomerDto dto) {
        CustomerEntity entity = modelMapper.map(dto, CustomerEntity.class);
        entity.setLifetimeBuyAmount(0);
        entity.setTotalTransactionCount(0L);
        entity.setTotalDue(0);
        entity.setCustomerValue(getCustomerValue(entity));
        CustomerEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> getAll(PagingAndSorting ps) {
        List<CustomerEntity> entities = repository.findAll(appUtils.getPageable(ps)).getContent();
        if (entities.isEmpty())
            return null;

        return entities.stream()
                .map(entity -> modelMapper.map(entity, CustomerDto.class))
                .toList();
    }

    @Override
    public CustomerDto getById(Long id) {
        throw new MethodBlockedException();
    }

    @Override
    public CustomerDto updateById(Long id, CustomerDto updatedDto) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        if (updatedDto.getPhone() != null) {
            CustomerEntity customer = repository.findByPhone(updatedDto.getPhone()).orElse(null);
            if (customer == null)
                entity.setPhone(updatedDto.getPhone());
        }
        if (updatedDto.getCustomerName() != null)
            entity.setCustomerName(updatedDto.getCustomerName());
        if (updatedDto.getAddress() != null)
            entity.setAddress(updatedDto.getAddress());
        if (updatedDto.getEmail() != null)
            entity.setEmail(updatedDto.getEmail());

        return modelMapper.map(repository.save(entity), CustomerDto.class);
    }

    @Override
    public void deleteById(Long id) {
        throw new MethodBlockedException();
    }

    @Override
    public CustomerDto getByPhone(String phone) {
        CustomerEntity entity = repository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with phone: " + phone));

        return modelMapper.map(entity, CustomerDto.class);
    }

    @Override
    public Double getTotalDueForAll() {
        return repository.findSumByTotalDue()
                .orElseThrow(() -> new RuntimeException("Could not get the total due from repository"));
    }

    @Override
    public Double getCustomerValue(CustomerEntity entity) {

        double lba = entity.getLifetimeBuyAmount();
        Long ttc = entity.getTotalTransactionCount();
        double td = entity.getTotalDue();

        double maxLba = repository.findMaxLifetimeBuyAmount().orElse(0.1);
        if (maxLba == 0.0) maxLba = 0.1;
        double minLba = repository.findMinLifetimeBuyAmount().orElse(0.0);

        double maxTtc = repository.findMaxTotalTransactionCount().orElse(1L);
        if (maxTtc == 0.0) maxTtc = 0.1;
        double minTtc = repository.findMinTotalTransactionCount().orElse(0L);

        double maxTd = repository.findMaxTotalDue().orElse(0.1);
        if (maxTd == 0.0) maxTd = 0.1;
        double minTd = repository.findMinTotalDue().orElse(0.0);

        double normLba = (lba - minLba) / (maxLba - minLba);
        double normTtc = (ttc - minTtc) / (maxTtc - minTtc);
        double normTd = (td - minTd) / (maxTd - minTd);

        double weightLba = 0.6;
        double weightTtc = 0.3;
        double weightTd = 0.1;

        double scoreLba = normLba * weightLba;
        double scoreTtc = normTtc * weightTtc;
        double scoreTd = normTd * weightTd;

        double totalScore = scoreLba + scoreTtc + scoreTd;

        return totalScore * 10;
    }
}
