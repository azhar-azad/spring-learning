package com.azad.estatement.models.entities;

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
@Table(name = "usr_mapping", uniqueConstraints = { 
		@UniqueConstraint(name = "UniqueOrgIdAndUsrIdAndCifNum", columnNames = { "orgId", "usrId", "cifNum" })
})
public class UsrMappingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usrMappingId;

	@Column(nullable = false)
	private Long orgId;
	
	@Column(nullable = false)
	private Long usrId;
	
	@Column(nullable = false)
	private String cifNum;
}
