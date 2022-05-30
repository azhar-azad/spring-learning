package com.azad.bankapi.models.entities;

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
@Table(name = "account", uniqueConstraints = {
		@UniqueConstraint(name = "UniqueAcctNumberAndType", columnNames = { "acctNumber", "type" })
})
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long acctId;
	
	@Column(nullable = false)
	private Long acctNumber;
	
	@Column(nullable = false)
	private String type;
	
	@Column
	private String status;
}
