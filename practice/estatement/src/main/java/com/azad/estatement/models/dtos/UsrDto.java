package com.azad.estatement.models.dtos;

import com.azad.estatement.models.Organization;
import com.azad.estatement.models.Usr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrDto extends Usr {

	private Long usrId;
	private String cifNum;
	private Organization org;
}
