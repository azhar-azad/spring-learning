package com.azad.estatement.models.responses;

import com.azad.estatement.models.Organization;
import com.azad.estatement.models.Usr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrResponse extends Usr {

	private Long usrId;
	private String cifNum;
	private Organization org;
}
