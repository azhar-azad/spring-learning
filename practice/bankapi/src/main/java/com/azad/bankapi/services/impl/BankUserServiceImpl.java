package com.azad.bankapi.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.bankapi.data.AddressRepository;
import com.azad.bankapi.data.BankUserRepository;
import com.azad.bankapi.exceptions.ResourceCreationFailedException;
import com.azad.bankapi.exceptions.ResourceNotFoundException;
import com.azad.bankapi.models.dtos.BankUserDto;
import com.azad.bankapi.models.entities.AddressEntity;
import com.azad.bankapi.models.entities.BankUserEntity;
import com.azad.bankapi.services.BankUserService;
import com.azad.bankapi.utils.AppUtils;
import com.azad.bankapi.utils.PagingAndSorting;

@Service
public class BankUserServiceImpl implements BankUserService {
	
	@Autowired
	private AppUtils appUtils;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AddressRepository addressRepository;
	
	private BankUserRepository bankUserRepository;
	
	@Autowired
	public BankUserServiceImpl(BankUserRepository bankUserRepository) {
		this.bankUserRepository = bankUserRepository;
	}

	@Override
	public BankUserDto create(BankUserDto dto) {

		AddressEntity address = addressRepository.save(mapper.map(dto.getAddress(), AddressEntity.class));
		
		BankUserEntity bankUser = mapper.map(dto, BankUserEntity.class);
		bankUser.setAddress(address);
		
		int userAge = appUtils.getBankUserAge(dto.getDateOfBirth());
		if (userAge < 18) {
			throw new ResourceCreationFailedException("User has to be 18 years or more. Age is " + userAge);
		}
		
		BankUserEntity updatedBankUser = bankUserRepository.save(bankUser);
		
		addressRepository.clearAddressRepository();
			
		return mapper.map(updatedBankUser, BankUserDto.class);
	}

	@Override
	public List<BankUserDto> getAll(PagingAndSorting ps) {

		Pageable pageable = null;
		if (ps.getSort().isEmpty()) {
			pageable = PageRequest.of(ps.getPage(), ps.getLimit());
		} else {
			Sort sortBy = appUtils.getSortBy(ps.getSort(), ps.getOrder());
			pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sortBy);
		}
		
		List<BankUserEntity> bankUsers = bankUserRepository.findAll(pageable).getContent();
		
		List<BankUserDto> dtos = new ArrayList<>();
		bankUsers.forEach(bankUser -> dtos.add(mapper.map(bankUser, BankUserDto.class)));
		
		return dtos;
	}

	@Override
	public BankUserDto getById(Long bankUserId) {

		BankUserEntity bankUser = bankUserRepository.findById(bankUserId).orElseThrow(() -> new ResourceNotFoundException("BankUser", bankUserId));
		
		if (bankUser == null) {
			return null;
		}
		
		return mapper.map(bankUser, BankUserDto.class);
	}

	@Override
	public BankUserDto updateById(Long bankUserId, BankUserDto dto) {

		BankUserEntity bankUser = bankUserRepository.findById(bankUserId).orElseThrow(() -> new ResourceNotFoundException("BankUser", bankUserId));
		
		if (dto.getFirstName() != null) {
			bankUser.setFirstName(dto.getFirstName());
		} 
		if (dto.getLastName() != null) {
			bankUser.setLastName(dto.getLastName());
		}
		if (dto.getEmail() != null) {
			bankUser.setEmail(dto.getEmail());
		}
		if (dto.getPhoneNumber() != null) {
			bankUser.setPhoneNumber(dto.getPhoneNumber());
		}
		if (dto.getDateOfBirth() != null) {
			int userAge = appUtils.getBankUserAge(dto.getDateOfBirth());
			if (userAge < 18) {
				throw new ResourceCreationFailedException("User has to be 18 years or more. Age is " + userAge);
			}
			bankUser.setDateOfBirth(dto.getDateOfBirth());
		}
		if (dto.getAddress() != null) {
			bankUser.setAddress(mapper.map(dto.getAddress(), AddressEntity.class));
		}
		
		BankUserEntity updatedBankUser = bankUserRepository.save(bankUser);
		
		// Delete the addresses entry that are not mapped in the bank_user table
		addressRepository.clearAddressRepository();
		
		return mapper.map(updatedBankUser, BankUserDto.class);
	}

	@Override
	public void deleteById(Long bankUserId) {
		
		BankUserEntity bankUser = bankUserRepository.findById(bankUserId).orElseThrow(() -> new ResourceNotFoundException("BankUser", bankUserId));
		
		bankUserRepository.delete(bankUser);
	}

}
