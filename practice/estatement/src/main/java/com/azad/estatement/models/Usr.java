package com.azad.estatement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usr {

	protected String usrSsn;
	
	protected String usrFirstname;
	
	protected String usrLastname;
	
	protected String usrMiddlename;
	
	protected Character type;
}
