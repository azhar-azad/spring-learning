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
import com.azad.estatement.models.dtos.OrgDto;
import com.azad.estatement.models.entities.OrganizationEntity;
import com.azad.estatement.repos.OrgRepository;
import com.azad.estatement.services.OrgService;
import com.azad.estatement.utils.AppUtils;

@Service
public class OrgServiceImpl implements OrgService {
	
	private ModelMapper modelMapper;
	
	private OrgRepository orgRepository;
	
	@Autowired
	public OrgServiceImpl(ModelMapper modelMapper, OrgRepository orgRepository) {
		this.modelMapper = modelMapper;
		this.orgRepository = orgRepository;
	}

	@Override
	public OrgDto create(OrgDto orgDto) {

		OrganizationEntity org = orgRepository.save(modelMapper.map(orgDto, OrganizationEntity.class));
		
		if (org == null) {
			throw new ResourceCreationFailedException("Failed to create the new resource; ENTITY: Organization");
		}
		
		return modelMapper.map(org, OrgDto.class);
	}

	@Override
	public List<OrgDto> getAll(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		
		List<OrgDto> orgDtos = new ArrayList<>();
		orgRepository.findAll(pageable).getContent()
			.forEach(org -> orgDtos.add(modelMapper.map(org, OrgDto.class)));
		
		return orgDtos;
	}

	@Override
	public List<OrgDto> getAll(int page, int limit, String sort, String order) {

		Sort sortBy = AppUtils.getSortBy(sort, order);
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		
		List<OrgDto> orgDtos = new ArrayList<>();
		orgRepository.findAll(pageable).getContent()
			.forEach(org -> orgDtos.add(modelMapper.map(org, OrgDto.class)));
		
		return orgDtos;
	}

	@Override
	public OrgDto getById(Long orgId) {

		OrganizationEntity org = orgRepository.findById(orgId).orElseThrow(() -> new ResourceNotFoundException("Organization", orgId));
		
		return modelMapper.map(org, OrgDto.class);
	}

	@Override
	public OrgDto updateById(Long orgId, OrgDto orgDto) {

		OrganizationEntity org = orgRepository.findById(orgId).orElseThrow(() -> new ResourceNotFoundException("Organization", orgId));
		
		if (orgDto.getOrgUniquename() != null) {
			org.setOrgUniquename(orgDto.getOrgUniquename());
		}
		if (orgDto.getOrgDisplayname() != null) {
			org.setOrgDisplayname(orgDto.getOrgDisplayname());
		}
		if (orgDto.getOrgServerName() != null) {
			org.setOrgServerName(orgDto.getOrgServerName());
		}
		if (orgDto.getSchemaName() != null) {
			org.setSchemaName(orgDto.getSchemaName());
		}
		
		return modelMapper.map(orgRepository.save(org), OrgDto.class);
	}

	@Override
	public void deleteById(Long orgId) {

		OrganizationEntity org = orgRepository.findById(orgId).orElseThrow(() -> new ResourceNotFoundException("Organization", orgId));
		
		orgRepository.delete(org);
	}

}
