package com.azad.estatement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrMapping {

	private Long orgId;
	private Long usrId;
	private String cifNum;
}
