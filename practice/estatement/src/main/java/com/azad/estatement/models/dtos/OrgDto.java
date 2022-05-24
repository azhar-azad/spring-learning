package com.azad.estatement.models.dtos;

import com.azad.estatement.models.Organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgDto extends Organization {

	private Long orgId;
}
