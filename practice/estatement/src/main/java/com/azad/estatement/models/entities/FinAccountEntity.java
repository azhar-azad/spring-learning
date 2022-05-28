package com.azad.estatement.models.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fin_account", uniqueConstraints = { 
		@UniqueConstraint(name = "UniqueAcctNumberAndOrgId", columnNames = { "acctNumber", "org_id" })
})
public class FinAccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long acctId;
	
	@Column(nullable = false)
	private Long acctNumber;
	
	@Column(length = 30, nullable = false)
	private String acctOwnerName;
	
	@Column(length = 10)
	private String type;
	
	@Column
	private LocalDate lastModified;
	
	@Column(length = 1)
	private Character status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	private OrganizationEntity org;
}
