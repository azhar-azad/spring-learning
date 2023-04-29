package com.azad.moneymanagerapi.services.impl;

import com.azad.moneymanagerapi.commons.AppUtils;
import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.constants.CategoryTypes;
import com.azad.moneymanagerapi.models.dtos.TransactionDto;
import com.azad.moneymanagerapi.models.entities.AccountEntity;
import com.azad.moneymanagerapi.models.entities.CategoryEntity;
import com.azad.moneymanagerapi.models.entities.SubcategoryEntity;
import com.azad.moneymanagerapi.models.entities.TransactionEntity;
import com.azad.moneymanagerapi.repositories.AccountRepository;
import com.azad.moneymanagerapi.repositories.CategoryRepository;
import com.azad.moneymanagerapi.repositories.SubcategoryRepository;
import com.azad.moneymanagerapi.repositories.TransactionRepository;
import com.azad.moneymanagerapi.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    private final TransactionRepository repository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionDto create(TransactionDto dto) {

        dto.setTransactionType(dto.getTransactionType().toUpperCase());

        AccountEntity account = accountRepository.findByAccountName(dto.getAccountName()).orElseThrow(
                () -> new RuntimeException("Account not found with name: " + dto.getAccountName()));
        CategoryEntity category = categoryRepository.findByCategoryName(dto.getCategoryName()).orElseThrow(
                () -> new RuntimeException("Category not found with name: " + dto.getCategoryName()));

        if (!category.getType().equalsIgnoreCase(dto.getTransactionType())) {
            throw new RuntimeException("Cannot create a " + dto.getTransactionType() + " transaction for " + category.getCategoryName() + " category");
        }

        SubcategoryEntity subcategory = null;
        if (dto.getSubcategoryName() != null) {
            subcategory = subcategoryRepository.findBySubcategoryName(dto.getSubcategoryName()).orElseThrow(
                    () -> new RuntimeException("Subcategory not found with name: " + dto.getSubcategoryName()));
            if (!subcategory.getCategory().getCategoryName().equalsIgnoreCase(category.getCategoryName())) {
                throw new RuntimeException(subcategory.getSubcategoryName() + " is not a subcategory of " + category.getCategoryName() + " category");
            }
        }

        TransactionEntity entity = modelMapper.map(dto, TransactionEntity.class);
        entity.setAccount(account);
        entity.setCategory(category);
        entity.setSubcategory(subcategory);

        TransactionEntity savedEntity = repository.save(entity);

        updateAccountBalance(account, savedEntity.getAmount(), savedEntity.getTransactionType());

        TransactionDto savedDto = modelMapper.map(savedEntity, TransactionDto.class);
        savedDto.setAccountName(savedEntity.getAccount().getAccountName());
        savedDto.setCategoryName(savedEntity.getCategory().getCategoryName());
        if (subcategory != null)
            savedDto.setSubcategoryName(savedEntity.getSubcategory().getSubcategoryName());
        return savedDto;
    }

    @Override
    public List<TransactionDto> getAll(PagingAndSorting ps) {

        Pageable pageable = appUtils.getPageable(ps);

        List<TransactionEntity> entitiesFromDb = repository.findAll(pageable).getContent();
        if (entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> modelMapper.map(entity, TransactionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getAllByTransactionType(PagingAndSorting ps, String transactionType) {

        Pageable pageable = appUtils.getPageable(ps);

        Optional<List<TransactionEntity>> optionalTransactionEntities = repository.findByTransactionType(transactionType, pageable);
        if (!optionalTransactionEntities.isPresent()) {
            return null;
        }

        List<TransactionEntity> entitiesFromDb = optionalTransactionEntities.get();
        if (entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> {
                    TransactionDto dto = modelMapper.map(entity, TransactionDto.class);
                    dto.setAccountName(entity.getAccount().getAccountName());
                    dto.setCategoryName(entity.getCategory().getCategoryName());
                    if (entity.getSubcategory() != null)
                        dto.setSubcategoryName(entity.getSubcategory().getSubcategoryName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {

        TransactionEntity entityFromDb = repository.findById(id).orElse(null);
        if (entityFromDb == null)
            return null;

        TransactionDto savedDto = modelMapper.map(entityFromDb, TransactionDto.class);
        savedDto.setAccountName(entityFromDb.getAccount().getAccountName());
        savedDto.setCategoryName(entityFromDb.getCategory().getCategoryName());
        if (entityFromDb.getSubcategory() != null)
            savedDto.setSubcategoryName(entityFromDb.getSubcategory().getSubcategoryName());
        return savedDto;
    }

    @Override
    public TransactionDto updateById(Long id, TransactionDto updatedDto) {

        TransactionEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Transaction not found with id: " + id));

        if (updatedDto.getDate() != null)
            entityFromDb.setDate(updatedDto.getDate());
        if (updatedDto.getAmount() != null)
            entityFromDb.setAmount(updatedDto.getAmount());
        if (updatedDto.getNote() != null)
            entityFromDb.setNote(updatedDto.getNote());
        if (updatedDto.getDescription() != null)
            entityFromDb.setDescription(updatedDto.getDescription());
        if (updatedDto.getTransactionType() != null) {
            updatedDto.setTransactionType(updatedDto.getTransactionType().toUpperCase());
            entityFromDb.setTransactionType(updatedDto.getTransactionType());
        }

        if (updatedDto.getAccountName() != null && !updatedDto.getAccountName().equalsIgnoreCase(entityFromDb.getAccount().getAccountName())) {
            AccountEntity account = accountRepository.findByAccountName(updatedDto.getAccountName()).orElseThrow(
                    () -> new RuntimeException("Account not found with name: " + updatedDto.getAccountName()));
            entityFromDb.setAccount(account);
        }
        if (updatedDto.getCategoryName() != null && !updatedDto.getCategoryName().equalsIgnoreCase(entityFromDb.getCategory().getCategoryName())) {
            CategoryEntity category = categoryRepository.findByCategoryName(updatedDto.getCategoryName()).orElseThrow(
                    () -> new RuntimeException("Category not found with name: " + updatedDto.getCategoryName()));
            entityFromDb.setCategory(category);
        }
        if (updatedDto.getSubcategoryName() != null && !updatedDto.getSubcategoryName().equalsIgnoreCase(entityFromDb.getSubcategory().getSubcategoryName())) {
            SubcategoryEntity subcategory = subcategoryRepository.findBySubcategoryName(updatedDto.getSubcategoryName()).orElseThrow(
                    () -> new RuntimeException("Subcategory not found with name: " + updatedDto.getSubcategoryName()));
            entityFromDb.setSubcategory(subcategory);
        }

        TransactionEntity updatedEntity = repository.save(entityFromDb);

        if (updatedDto.getAmount() != null) {
            updateAccountBalance(updatedEntity.getAccount(), updatedEntity.getAmount(), updatedEntity.getTransactionType());
        }

        return modelMapper.map(updatedEntity, TransactionDto.class);
    }

    @Override
    public void deleteById(Long id) {

        TransactionEntity entityFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Transaction not found with id: " + id));

        repository.delete(entityFromDb);
        updateAccountBalance(entityFromDb.getAccount(), entityFromDb.getAmount(), entityFromDb.getTransactionType());
    }

    private void updateAccountBalance(AccountEntity account, Double amount, String transactionType) {

        if (transactionType.equalsIgnoreCase(CategoryTypes.INCOME.name()))
            account.setBalance(account.getBalance() + amount);
        else
            account.setBalance(account.getBalance() - amount);

        accountRepository.save(account);
    }
}
