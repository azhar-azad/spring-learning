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
import com.azad.estatement.models.Organization;
import com.azad.estatement.models.dtos.OrgDto;
import com.azad.estatement.models.dtos.UsrDto;
import com.azad.estatement.models.dtos.UsrMappingDto;
import com.azad.estatement.models.entities.OrganizationEntity;
import com.azad.estatement.models.entities.UsrEntity;
import com.azad.estatement.models.entities.UsrMappingEntity;
import com.azad.estatement.models.responses.UsrResponsesWithOrg;
import com.azad.estatement.repos.UsrMappingRepository;
import com.azad.estatement.repos.UsrRepository;
import com.azad.estatement.services.OrgService;
import com.azad.estatement.services.UsrService;
import com.azad.estatement.utils.ApiUtils;
import com.azad.estatement.utils.AppUtils;
import com.azad.estatement.utils.PagingAndSorting;

@Service
public class UsrServiceImpl implements UsrService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiUtils apiUtils;
	
	@Autowired
	private UsrMappingRepository usrMappingRepository;

	private UsrRepository usrRepository;
	
	@Autowired
	public UsrServiceImpl(UsrRepository usrRepository) {
		this.usrRepository = usrRepository;
	}

	@Override
	public UsrDto create(String orgName, UsrDto usrDto) {

		UsrEntity usr = usrRepository.save(modelMapper.map(usrDto, UsrEntity.class));
		
		if (usr == null) {
			throw new ResourceCreationFailedException("Failed to create the new resource; ENTITY: Usr");
		}
		
		OrgDto orgDto = modelMapper.map(apiUtils.getOrgByOrgName(orgName), OrgDto.class);
		
		UsrMappingEntity usrMapping = new UsrMappingEntity();
		usrMapping.setCifNum(usrDto.getCifNum());
		usrMapping.setOrgId(orgDto.getOrgId());
		usrMapping.setUsrId(usr.getUsrId());
		
		UsrMappingDto usrMappingDto = modelMapper.map(usrMappingRepository.save(usrMapping), UsrMappingDto.class);
		
		UsrDto savedUsrDto = modelMapper.map(usr, UsrDto.class);
		savedUsrDto.setCifNum(usrMappingDto.getCifNum());
		savedUsrDto.setOrg(modelMapper.map(orgDto, Organization.class));
		
		return savedUsrDto;
	}

	@Override
	public List<UsrDto> getAll(String orgName, PagingAndSorting ps) {
		
		Pageable pageable = null;
		
		if (ps.getSort().isEmpty()) {
			pageable = PageRequest.of(ps.getPage(), ps.getLimit());
		} else {
			Sort sortBy = AppUtils.getSortBy(ps.getSort(), ps.getOrder());
			pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sortBy);
		}
		
		List<UsrEntity> usrs = usrRepository.findUsrsByOrgName(pageable, orgName);
		
		List<UsrDto> usrDtos = new ArrayList<>();
		usrs.forEach(usr -> usrDtos.add(modelMapper.map(usr, UsrDto.class)));
		
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
		
		// Delete from usr_mapping table
		usrMappingRepository.deleteByUsrId(usrId);
		
		usrRepository.delete(usr);
	}

	@Override
	public UsrDto create(UsrDto usrDto) {
		return null;
	}

	@Override
	public List<UsrDto> getAll(PagingAndSorting ps) {
		return null;
	}

}
