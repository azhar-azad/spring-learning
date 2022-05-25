package com.azad.estatement.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.estatement.exceptions.ResourceCreationFailedException;
import com.azad.estatement.exceptions.ResourceNotFoundException;
import com.azad.estatement.models.dtos.UsrDto;
import com.azad.estatement.models.entities.UsrEntity;
import com.azad.estatement.repos.UsrRepository;
import com.azad.estatement.services.UsrService;
import com.azad.estatement.utils.AppUtils;

@Service
public class UsrServiceImpl implements UsrService {
	
	private ModelMapper modelMapper;
	private UsrRepository usrRepository;
	
	@Autowired
	public UsrServiceImpl(ModelMapper modelMapper, UsrRepository usrRepository) {
		this.modelMapper = modelMapper;
		this.usrRepository = usrRepository;
	}

	@Override
	public UsrDto create(UsrDto usrDto) {

		UsrEntity usr = usrRepository.save(modelMapper.map(usrDto, UsrEntity.class));
		
		if (usr == null) {
			throw new ResourceCreationFailedException("Failed to create the new resource; ENTITY: Usr");
		}
		
		return modelMapper.map(usr, UsrDto.class);
	}

	@Override
	public List<UsrDto> getAll(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		
		List<UsrDto> usrDtos = new ArrayList<>();
		usrRepository.findAll(pageable).getContent()
			.forEach(usr -> usrDtos.add(modelMapper.map(usr, UsrDto.class)));
		
		return usrDtos;
	}

	@Override
	public List<UsrDto> getAll(int page, int limit, String sort, String order) {

		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<UsrDto> usrDtos = new ArrayList<>();
		usrRepository.findAll(pageable).getContent()
			.forEach(usr -> usrDtos.add(modelMapper.map(usr, UsrDto.class)));
		
		return usrDtos;
	}

	@Override
	public UsrDto getById(Long usrId) {

		UsrEntity usr = usrRepository.findById(usrId).orElseThrow(() -> new ResourceNotFoundException("Usr", usrId));
		
		return modelMapper.map(usr, UsrDto.class);
	}

	@Override
	public UsrDto updateById(Long usrId, UsrDto usrDto) {

		UsrEntity usr = usrRepository.findById(usrId).orElseThrow(() -> new ResourceNotFoundException("Usr", usrId));
		
		if (usrDto.getUsrSsn() != null) {
			usr.setUsrSsn(usrDto.getUsrSsn());
		}
		if (usrDto.getUsrFirstname() != null) {
			usr.setUsrFirstname(usrDto.getUsrFirstname());
		}
		if (usrDto.getUsrLastname() != null) {
			usr.setUsrLastname(usrDto.getUsrLastname());
		}
		if (usrDto.getUsrMiddlename() != null) {
			usr.setUsrMiddlename(usrDto.getUsrMiddlename());
		}
		if (usrDto.getType() != null) {
			usr.setType(usrDto.getType());
		}
		
		return modelMapper.map(usrRepository.save(usr), UsrDto.class);
	}

	@Override
	public void deleteById(Long usrId) {

		UsrEntity usr = usrRepository.findById(usrId).orElseThrow(() -> new ResourceNotFoundException("Usr", usrId));
		
		usrRepository.delete(usr);
	}

}
