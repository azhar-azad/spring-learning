package com.azad.estatement.services;

import java.util.List;

import com.azad.estatement.models.dtos.UsrDto;
import com.azad.estatement.utils.PagingAndSorting;

public interface UsrService extends GenericService<UsrDto> {

	UsrDto create(String orgName, UsrDto usrDto);
	
	List<UsrDto> getAll(String orgName, PagingAndSorting pagingAndSorting);
}
