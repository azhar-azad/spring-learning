package com.azad.cineplex.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Show {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String slot;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hall_id", referencedColumnName = "id")
	private Hall hall;
	
	@ManyToMany
	@JoinTable(
			name = "show_customer",
			joinColumns = @JoinColumn(name = "show_id"),
			inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private List<Customer> customers;
}
