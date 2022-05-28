package com.azad.estatement.services;

import com.azad.estatement.models.dtos.OrgDto;

public interface OrgService extends GenericService<OrgDto> {

	OrgDto getOrgByOrgName(String orgName);

}
