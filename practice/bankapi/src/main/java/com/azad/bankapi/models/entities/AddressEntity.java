package com.azad.bankapi.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Long addressId;
	
	@Column
	private String house;
	
	@Column
	private String road;
	
	@Column(nullable = false)
	private String area;
	
	@Column
	private String city;
	
	@Column(nullable = false)
	private String upazila;
	
	@Column(nullable = false)
	private String district;
	
	@OneToOne(mappedBy = "address")
	private BankUserEntity bankUser;
}
