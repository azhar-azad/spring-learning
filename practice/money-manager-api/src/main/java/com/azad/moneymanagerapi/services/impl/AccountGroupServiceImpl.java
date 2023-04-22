package com.azad.moneymanagerapi.services.impl;

import com.azad.moneymanagerapi.commons.AppUtils;
import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.AccountGroupDto;
import com.azad.moneymanagerapi.models.entities.AccountGroupEntity;
import com.azad.moneymanagerapi.repositories.AccountGroupRepository;
import com.azad.moneymanagerapi.services.AccountGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountGroupServiceImpl implements AccountGroupService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final AccountGroupRepository repository;

    @Autowired
    public AccountGroupServiceImpl(AccountGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountGroupDto create(AccountGroupDto dto) {

        AccountGroupEntity entity = modelMapper.map(dto, AccountGroupEntity.class);

        AccountGroupEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, AccountGroupDto.class);
    }

    @Override
    public List<AccountGroupDto> getAll(PagingAndSorting ps) {

        Pageable pageable = appUtils.getPageable(ps);

        List<AccountGroupEntity> allAccountGroupsFromDb = repository.findAll(pageable).getContent();
        if (allAccountGroupsFromDb.size() == 0)
            return null;

        return allAccountGroupsFromDb.stream()
                .map(entity -> modelMapper.map(entity, AccountGroupDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountGroupDto getById(Long id) {

        AccountGroupEntity accountGroupFromDb = repository.findById(id).orElse(null);
        if (accountGroupFromDb == null)
            return null;

        return modelMapper.map(accountGroupFromDb, AccountGroupDto.class);
    }

    @Override
    public AccountGroupDto updateById(Long id, AccountGroupDto updatedDto) {

        AccountGroupEntity accountGroupFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("AccountGroup not found with id: " + id));

        if (updatedDto.getAccountGroupName() != null)
            accountGroupFromDb.setAccountGroupName(updatedDto.getAccountGroupName());

        AccountGroupEntity updatedAccountGroup = repository.save(accountGroupFromDb);

        return modelMapper.map(updatedAccountGroup, AccountGroupDto.class);
    }

    @Override
    public void deleteById(Long id) {

        AccountGroupEntity accountGroupFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("AccountGroup not found with id: " + id));

        repository.delete(accountGroupFromDb);
    }
}
