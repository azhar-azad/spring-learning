package com.azad.estatement.models.responses;

import java.util.List;

import com.azad.estatement.models.Organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrResponsesWithOrg {

	private List<UsrResponse> usrs;
	private Organization org;
}
