package com.azad.estatement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azad.estatement.models.Organization;
import com.azad.estatement.services.OrgService;

@Component
public class ApiUtils {

	@Autowired
	private OrgService orgService;
	
	public Organization getOrgByOrgName(String orgName) {
		
		return orgService.getOrgByOrgName(orgName);
	}
}
