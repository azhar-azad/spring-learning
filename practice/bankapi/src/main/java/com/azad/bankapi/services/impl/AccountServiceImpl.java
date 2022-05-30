package com.azad.bankapi.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.bankapi.data.AccountRepository;
import com.azad.bankapi.exceptions.ResourceCreationFailedException;
import com.azad.bankapi.exceptions.ResourceNotFoundException;
import com.azad.bankapi.models.dtos.AccountDto;
import com.azad.bankapi.models.entities.AccountEntity;
import com.azad.bankapi.services.AccountService;
import com.azad.bankapi.utils.AppUtils;
import com.azad.bankapi.utils.PagingAndSorting;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AppUtils appUtils;
	
	private AccountRepository acctRepository;
	
	@Autowired
	public AccountServiceImpl(AccountRepository acctRepository) {
		this.acctRepository = acctRepository;
	}

	@Override
	public AccountDto create(AccountDto dto) {

		AccountEntity acct = acctRepository.save(modelMapper.map(dto, AccountEntity.class));
		
		if (acct == null) {
			throw new ResourceCreationFailedException("Failed to create the new resource; ENTITY: Account");
		}
		
		return modelMapper.map(acct, AccountDto.class);
	}

	@Override
	public List<AccountDto> getAll(PagingAndSorting ps) {

		Pageable pageable = null;
		
		if (ps.getSort().isEmpty()) {
			pageable = PageRequest.of(ps.getPage(), ps.getLimit());
		} else {
			Sort sortBy = appUtils.getSortBy(ps.getSort(), ps.getOrder());
			pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sortBy);
		}
		
		List<AccountDto> dtos = new ArrayList<>();
		acctRepository.findAll(pageable).forEach(acct -> dtos.add(modelMapper.map(acct, AccountDto.class)));
		
		return dtos;
	}

	@Override
	public AccountDto getById(Long acctId) {

		AccountEntity acct = acctRepository.findById(acctId).orElseThrow(() -> new ResourceNotFoundException("Account", acctId));
		
		return modelMapper.map(acct, AccountDto.class);
	}

	@Override
	public AccountDto updateById(Long acctId, AccountDto dto) {

		AccountEntity acct = acctRepository.findById(acctId).orElseThrow(() -> new ResourceNotFoundException("Account", acctId));
		
		if (dto.getAcctNumber() != null) {
			acct.setAcctNumber(dto.getAcctNumber());
		} 
		if (dto.getType() != null) {
			acct.setType(dto.getType());
		}
		if (dto.getStatus() != null) {
			acct.setStatus(dto.getStatus());
		}
		
		return modelMapper.map(acctRepository.save(acct), AccountDto.class);
	}

	@Override
	public void deleteById(Long acctId) {

		AccountEntity acct = acctRepository.findById(acctId).orElseThrow(() -> new ResourceNotFoundException("Account", acctId));
		
		acctRepository.delete(acct);
	}

}
