package com.azad.bankapi.models.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_user")
public class BankUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bankUserId;
	
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@Column(nullable = false, length = 40, unique = true)
	private String email;
	
	@Column(nullable = false, length = 14)
	private String phoneNumber;
	
	@Column(nullable = false)
	private LocalDate dateOfBirth;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", referencedColumnName = "address_id")
	private AddressEntity address;
}
