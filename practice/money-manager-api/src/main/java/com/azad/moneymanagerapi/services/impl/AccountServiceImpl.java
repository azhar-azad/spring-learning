package com.azad.moneymanagerapi.services.impl;

import com.azad.moneymanagerapi.commons.AppUtils;
import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.AccountDto;
import com.azad.moneymanagerapi.models.dtos.AccountDto;
import com.azad.moneymanagerapi.models.entities.AccountEntity;
import com.azad.moneymanagerapi.models.entities.AccountGroupEntity;
import com.azad.moneymanagerapi.repositories.AccountGroupRepository;
import com.azad.moneymanagerapi.repositories.AccountRepository;
import com.azad.moneymanagerapi.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AccountGroupRepository accountGroupRepository;

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountDto create(AccountDto dto) {

        AccountGroupEntity accountGroup = accountGroupRepository.findByAccountGroupName(dto.getAccountGroupName()).orElseThrow(
                () -> new RuntimeException("Account Group not found with name: " + dto.getAccountGroupName()));

        AccountEntity entity = modelMapper.map(dto, AccountEntity.class);
        entity.setAccountGroup(accountGroup);

        AccountEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, AccountDto.class);
    }

    @Override
    public List<AccountDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<AccountEntity> allEntitiesFromDb = repository.findAll(pageable).getContent();
        if (allEntitiesFromDb.size() == 0)
            return null;

        return allEntitiesFromDb.stream()
                .map(entity -> modelMapper.map(entity, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {

        AccountEntity entityFromDb = repository.findById(id).orElse(null);
        if (entityFromDb == null)
            return null;

        return modelMapper.map(entityFromDb, AccountDto.class);
    }

    @Override
    public AccountDto updateById(Long id, AccountDto updatedDto) {

        AccountEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found with id: " + id));

        if (updatedDto.getAccountName() != null)
            entityFromDb.setAccountName(updatedDto.getAccountName());
        if (updatedDto.getBalance() != null)
            entityFromDb.setBalance(updatedDto.getBalance());
        if (updatedDto.getCurrency() != null)
            entityFromDb.setCurrency(updatedDto.getCurrency());
        if (updatedDto.getDescription() != null)
            entityFromDb.setDescription(updatedDto.getDescription());

        AccountEntity updatedEntity = repository.save(entityFromDb);

        return modelMapper.map(updatedEntity, AccountDto.class);
    }

    @Override
    public void deleteById(Long id) {

        AccountEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found with id: " + id));

        repository.delete(entityFromDb);
    }
}
