package com.azad.cineplex.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Director extends MoviePersonnel {

	private static final long serialVersionUID = 1L;
	
	@ManyToMany(mappedBy = "directors")
	private List<Movie> movies;
}
