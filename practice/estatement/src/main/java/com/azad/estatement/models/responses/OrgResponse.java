package com.azad.estatement.models.responses;

import com.azad.estatement.models.Organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgResponse extends Organization {

	private Long orgId;
}
