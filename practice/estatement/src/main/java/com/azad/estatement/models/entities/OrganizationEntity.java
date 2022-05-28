package com.azad.estatement.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class OrganizationEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orgId;
	
	@Column(nullable = false, unique = true, length = 4)
	private String orgUniquename;
	
	@Column(nullable = false)
	private String orgDisplayname;
	
	@Column
	private String orgServerName;
	
	@Column
	private String schemaName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "org", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FinAccountEntity> finAccts;
}
