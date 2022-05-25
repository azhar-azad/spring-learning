package com.azad.estatement.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usr", uniqueConstraints = { 
		@UniqueConstraint(name = "UniqueFullName", columnNames = { "usrFirstname", "usrLastName", "usrMiddlename" })
})
public class UsrEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usrId;

	@Column(length = 15)
	private String usrSsn;
	
	@Column(length = 30, nullable = false)
	private String usrFirstname;
	
	@Column
	private String usrLastname;
	
	@Column
	private String usrMiddlename;
	
	@Column
	private Character type;
}
