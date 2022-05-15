package com.azad.cineplex.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Firstname cannot be null")
	private String firstName;
	
	@NotNull(message = "Lastname cannot be null")
	private String lastName;
	
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email is not valid")
	private String email;
	
	@NotNull(message = "Phone number cannot be null")
	@Size(min = 11, max = 11, message = "Phone number should be 11 digits long")
	private String phoneNumber;
	
	@ManyToMany(mappedBy = "customers")
	private List<Show> shows;
}
